package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.PurchaseStatus;
import com.boutiqueseeds.boutiqueSeeds.repos.PurchaseStatusRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchasestatuses")
public class PurchaseStatusController {
    @Autowired
    PurchaseStatusRepo purchaseStatusRepo;

    @GetMapping
    public List<PurchaseStatus> purchaseStatusList() {
        return purchaseStatusRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public PurchaseStatus purchaseStatus(@PathVariable Long id) {
        return purchaseStatusRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/activecodes")
    public List<PurchaseStatus> activePurchaseStatusList() {
        List<PurchaseStatus> purchaseStatuses = purchaseStatusRepo.findAll();
        List<PurchaseStatus> activeCodes = new ArrayList<>();
        for (PurchaseStatus p : purchaseStatuses) {
            if (p.getActive() == true) {
                activeCodes.add(p);
            }
        }
        return activeCodes;
    }

    @GetMapping
    @RequestMapping("/code/{statusCode}")
    public PurchaseStatus getPurchaseStatusByCode(@PathVariable Long statusCode) {
        List<PurchaseStatus> purchaseStatusList = purchaseStatusRepo.findAll();
        PurchaseStatus purchaseStatus = new PurchaseStatus();
        Boolean foundPurchaseStatusCode = false;
        for (PurchaseStatus p : purchaseStatusList) {
            if (p.getStatusCode().equals(statusCode)) {
                purchaseStatus = p;
                foundPurchaseStatusCode = true;
            }
        }
        if (foundPurchaseStatusCode == true) {
            return purchaseStatus;
        }else{
            return null;
        }

    }

    @PostMapping
    public ResponseEntity addPurchaseStatus(@RequestBody final PurchaseStatus purchaseStatus) {
        purchaseStatusRepo.saveAndFlush(purchaseStatus);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public PurchaseStatus updatePurchaseStatus(@PathVariable Long id, @RequestBody PurchaseStatus purchaseStatus) {
        PurchaseStatus existingPurchaseStatus = purchaseStatusRepo.getOne(id);
        BeanUtils.copyProperties(purchaseStatus, existingPurchaseStatus, "id");
        return purchaseStatusRepo.saveAndFlush(existingPurchaseStatus);
    }
}
