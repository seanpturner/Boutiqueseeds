package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Purchase;
import com.boutiqueseeds.boutiqueSeeds.repos.PurchaseRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    PurchaseRepo purchaseRepo;

    @GetMapping
    public List<Purchase> purchaseList() {
        return purchaseRepo.findAll();
    }
    @GetMapping
    @RequestMapping("{id}")
    public Purchase getPurchase(@PathVariable Long id) {
        return purchaseRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/user/{id}")
    public List<Purchase> getPurchasesByUser(@PathVariable Long id) {
        List<Purchase> purchaseList = purchaseRepo.findAll();
        List<Purchase> userPurchases = new ArrayList<>();
        for (Purchase p : purchaseList) {
            if (p.getUserId().equals(id)) {
                userPurchases.add(p);
            }
        }
        return userPurchases;
    }
    @GetMapping
    @RequestMapping("/openPurchases")
    public List<Purchase> getOpenPurchases() {
        List<Purchase> purchaseList = purchaseRepo.findAll();
        List<Purchase> openPurchases = new ArrayList<>();
        for (Purchase p : purchaseList) {
            if (p.getOrderStatus()<200) {
                openPurchases.add(p);
            }
        }
        return openPurchases;
    }

    @GetMapping
    @RequestMapping("/openPurchases/{id}")
    public List<Purchase> getOpenPurchasesByUserId(@PathVariable Long id) {
        List<Purchase> purchaseList = purchaseRepo.findAll();
        List<Purchase> openCustomerPurchases = new ArrayList<>();
        for (Purchase p : purchaseList) {
            if (p.getOrderStatus()<200 && p.getUserId().equals(id)) {
                openCustomerPurchases.add(p);
            }
        }
        return openCustomerPurchases;
    }

    @PostMapping
    public ResponseEntity addPurchase(@RequestBody final Purchase purchase) {
        purchaseRepo.saveAndFlush(purchase);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Purchase updatePurchase (@PathVariable Long id, @RequestBody Purchase purchase) {
        Purchase existingPurchase = purchaseRepo.getOne(id);
        BeanUtils.copyProperties(purchase, existingPurchase, "id");
        return purchaseRepo.saveAndFlush(existingPurchase);
    }

}
