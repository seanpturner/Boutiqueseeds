package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Plant;
import com.boutiqueseeds.boutiqueSeeds.repos.PlantRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController {
    @Autowired
    private PlantRepo plantRepo;

    @GetMapping
    public List<Plant> plantList() {
        return plantRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Plant getPlant(@PathVariable Long id) {
        return plantRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/plant/{plantName}")
    public Plant getPlantByName(@PathVariable String plantName) {
        List<Plant> plantList = plantRepo.findAll();
        for (Plant p : plantList) {
            if (p.getName().equalsIgnoreCase(plantName)) {
                return p;
            }
        }
        return null;
    }

    @GetMapping
    @RequestMapping("/line/{lineId}")
    public List<Plant> getPlants(@PathVariable String lineId) {
        List<Plant> plantList = plantRepo.findAll();
        List<Plant> lineList = new ArrayList<>();
        for (Plant p : plantList) {
            int mLineNumber = p.getMaternalLine();
            int pLineNumber = p.getPaternalLine();
            if (mLineNumber == Integer.parseInt(lineId) || pLineNumber == Integer.parseInt(lineId)) {
                lineList.add(p);
            }
        }
        return lineList;
    }

    @GetMapping
    @RequestMapping("/sex/{sex}")
    public List<Plant> getPlantsBySex(@PathVariable String sex) {
        List<Plant> plantList = plantRepo.findAll();
        List<Plant> sexList = new ArrayList<>();
        for (Plant p : plantList) {
            String plantSex = p.getSex();
            if (plantSex.equalsIgnoreCase(sex)) {
                sexList.add(p);
            }
        }
        return sexList;
    }

    @GetMapping
    @RequestMapping("/clone/{clone}")
    public List<Plant> getPlantsByClone(@PathVariable String clone) {
        List<Plant> plantList = plantRepo.findAll();
        List<Plant> cloneList = new ArrayList<>();
        for (Plant p : plantList) {
            String plantClone = String.valueOf(p.getClone());
            if (plantClone.equalsIgnoreCase(clone)) {
                cloneList.add(p);
            }
        }
        return cloneList;
    }

    @PostMapping
    public ResponseEntity addPlant(@RequestBody final Plant plant) throws Exception {
        List<Plant> allPlants = plantRepo.findAll();
        for (Plant p : allPlants) {
            if (p.getName().equalsIgnoreCase(plant.getName())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if (p.getId() == plant.getId()) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        plantRepo.saveAndFlush(plant);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void removePlant(@PathVariable Long id) {
        plantRepo.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Plant updatePlant(@PathVariable Long id, @RequestBody Plant plant) {
        Plant existingPlant = plantRepo.getOne(id);
        BeanUtils.copyProperties(plant, existingPlant, "id");
        return plantRepo.saveAndFlush(existingPlant);
    }
}
