package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.PricingStructure;
import com.boutiqueseeds.boutiqueSeeds.repos.PricingStructureRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pricing")
public class PricingStructureController {
    @Autowired
    private PricingStructureRepo pricingStructureRepo;

    @GetMapping
    public List<PricingStructure> pricingStructureList() {
        return pricingStructureRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public PricingStructure getPricingById(@PathVariable Long id) {
        return pricingStructureRepo.getOne(id);
    }

    @PostMapping
    public ResponseEntity addPricing(@RequestBody final PricingStructure pricingStructure) {
        List<PricingStructure> allPricing = pricingStructureRepo.findAll();
        for (PricingStructure p : allPricing) {
            if (p.getLabel().equals(pricingStructure.getLabel())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        pricingStructureRepo.saveAndFlush(pricingStructure);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public PricingStructure updatePricing(@PathVariable Long id, @RequestBody PricingStructure pricingStructure) {
        PricingStructure existingPricing = pricingStructureRepo.getOne(id);
        BeanUtils.copyProperties(pricingStructure, existingPricing, "id");
        return pricingStructureRepo.saveAndFlush(existingPricing);
    }
}
