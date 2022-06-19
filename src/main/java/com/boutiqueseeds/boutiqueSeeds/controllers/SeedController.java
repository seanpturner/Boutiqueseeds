package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Seed;
import com.boutiqueseeds.boutiqueSeeds.repos.SeedRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seeds")
public class SeedController {
    @Autowired
    private SeedRepo seedRepo;

    @GetMapping
    public List<Seed> seedList() {
        return seedRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Seed getSeed(@PathVariable Long id) {
        return seedRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/seed/{seedName}")
    public Seed getSeedByName(@PathVariable String seedName) {
        List<Seed> seedList = seedRepo.findAll();
        for (Seed s : seedList) {
            if (s.getName().equalsIgnoreCase(seedName)) {
                return s;
            }
        }
        return null;
    }

    @GetMapping
    @RequestMapping("/line/{lineId}")
    public List<Seed> getSeeds(@PathVariable String lineId) {
        List<Seed> seedList = seedRepo.findAll();
        List<Seed> lineList = new ArrayList<>();
        for (Seed s : seedList) {
            int mLineNumber = s.getMaternalLine();
            int pLineNumber = s.getPaternalLine();
            if (mLineNumber == Integer.parseInt(lineId) || pLineNumber == Integer.parseInt(lineId)) {
                lineList.add(s);
            }
        }
        return lineList;
    }

    @GetMapping
    @RequestMapping("/mother/{mother}")
    public List<Seed> getSeedsByMother(@PathVariable String mother) {
        List<Seed> seedList = seedRepo.findAll();
        List<Seed> motherList = new ArrayList<>();
        for (Seed s : seedList) {

            if (s.getMother().equalsIgnoreCase(mother)) {
                motherList.add(s);
            }
        }
        return motherList;
    }

    @GetMapping
    @RequestMapping("/father/{father}")
    public List<Seed> getSeedsByFather(@PathVariable String father) {
        List<Seed> seedList = seedRepo.findAll();
        List<Seed> fatherList = new ArrayList<>();
        for (Seed s : seedList) {

            if (s.getFather().equalsIgnoreCase(father)) {
                fatherList.add(s);
            }
        }
        return fatherList;
    }

    @PostMapping
    public ResponseEntity addSeed(@RequestBody final Seed seed) throws Exception {
        List<Seed> allSeeds = seedRepo.findAll();
        for (Seed s : allSeeds) {
            if (s.getName().equalsIgnoreCase(seed.getName())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if (s.getId() == seed.getId()) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        seedRepo.saveAndFlush(seed);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void removeSeed(@PathVariable Long id) {
        seedRepo.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Seed updateSeed(@PathVariable Long id, @RequestBody Seed seed) {
        Seed existingSeed = seedRepo.getOne(id);
        BeanUtils.copyProperties(seed, existingSeed, "id");
        return seedRepo.saveAndFlush(existingSeed);
    }

    @RequestMapping(value = "/incrementCount/{seedId}/{qty}", method = RequestMethod.PUT)
    public Seed incrementSeedCount(@PathVariable Long seedId, @PathVariable Integer qty) {
        Seed existingSeed = seedRepo.getOne(seedId);
        Integer seedCount = existingSeed.getQuantityAvailable();
        seedCount = seedCount + qty;
        existingSeed.setQuantityAvailable(seedCount);
        return seedRepo.saveAndFlush(existingSeed);
    }

    @RequestMapping(value = "/decrementCount/{seedId}/{qty}", method = RequestMethod.PUT)
    public Seed decrementSeedCount(@PathVariable Long seedId, @PathVariable Integer qty) {
        Seed existingSeed = seedRepo.getOne(seedId);
        Integer seedCount = existingSeed.getQuantityAvailable();
        if (seedCount - qty >= 0) {
            seedCount = seedCount - qty;
            existingSeed.setQuantityAvailable(seedCount);
        }
        return seedRepo.saveAndFlush(existingSeed);
    }
}
