package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Purchase;
import com.boutiqueseeds.boutiqueSeeds.repos.PurchaseRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.boutiqueseeds.boutiqueSeeds.services.GenerateLocator.generateLocatorString;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    @RequestMapping("/openpurchases")
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
    @RequestMapping("/completedpurchasesbydaterange/{startYear}/{startMonth}/{startDay}/{endYear}/{endMonth}/{endDay}")
    public List<Purchase> getCompletedPurchasesByDateRange(@PathVariable Integer startYear, @PathVariable Integer startMonth, @PathVariable Integer startDay, @PathVariable Integer endYear, @PathVariable Integer endMonth, @PathVariable Integer endDay) {
        List<Purchase> purchaseList = purchaseRepo.findAll();
        List<Purchase> closedPurchases = new ArrayList<>();
        LocalDateTime sd = LocalDateTime.of(startYear, startMonth, startDay,0,0);
        LocalDateTime ed = LocalDateTime.of(endYear, endMonth, endDay,23 ,59);
        for (Purchase p : purchaseList) {
            if (p.getOrderStatus().equals(Long.valueOf(200))) {
                if (p.getPurchaseDate().isAfter(sd) || p.getPurchaseDate().isEqual(sd)) {
                    if (p.getPurchaseDate().isBefore(ed) || p.getPurchaseDate().isEqual(ed)) {
                        closedPurchases.add(p);
                    }
                }
            }
        }
        return closedPurchases;
    }

    @GetMapping
    @RequestMapping("/allpurchasesbydaterange/{startYear}/{startMonth}/{startDay}/{endYear}/{endMonth}/{endDay}")
    public List<Purchase> getAllPurchasesByDateRange(@PathVariable Integer startYear, @PathVariable Integer startMonth, @PathVariable Integer startDay, @PathVariable Integer endYear, @PathVariable Integer endMonth, @PathVariable Integer endDay) {
        List<Purchase> purchaseList = purchaseRepo.findAll();
        List<Purchase> closedPurchases = new ArrayList<>();
        LocalDateTime sd = LocalDateTime.of(startYear, startMonth, startDay,0,0);
        LocalDateTime ed = LocalDateTime.of(endYear, endMonth, endDay,23 ,59);
        for (Purchase p : purchaseList) {
            if (p.getPurchaseDate().isAfter(sd) || p.getPurchaseDate().isEqual(sd)) {
                if (p.getPurchaseDate().isBefore(ed) || p.getPurchaseDate().isEqual(ed)) {
                    closedPurchases.add(p);
                }
            }
        }
        return closedPurchases;
    }

    @GetMapping
    @RequestMapping("/openpurchases/{id}")
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

    @GetMapping
    @RequestMapping("/locator/{locator}")
    public Purchase getPurchaseByLocator(@PathVariable String locator) {
        List<Purchase> purchaseList = purchaseRepo.findAll();
        for (Purchase p : purchaseList) {
            if (p.getRecordLocator() != null) {
                if (p.getRecordLocator().equals(locator)) {
                    return p;
                }
            }
        }
        throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
    }

    @GetMapping
    @RequestMapping("/createLocator")
    public String getLocator () {
        return generateLocatorString();
    }

    @PostMapping
    public ResponseEntity addPurchase(@RequestBody final Purchase purchase) {
        List<Purchase> purchaseList = purchaseRepo.findAll();
        for (Purchase p : purchaseList) {
            if (p.getRecordLocator() == purchase.getRecordLocator()) {
                throw new ResponseStatusException(BAD_REQUEST, "Unable to create this purchase");
            }
        }
        purchaseRepo.saveAndFlush(purchase);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Purchase updatePurchase (@PathVariable Long id, @RequestBody Purchase purchase) {
        Purchase existingPurchase = purchaseRepo.getOne(id);
        BeanUtils.copyProperties(purchase, existingPurchase, "id");
        return purchaseRepo.saveAndFlush(existingPurchase);
    }

    @RequestMapping(value = "updatePayment/{locator}", method = RequestMethod.PUT)
    public Purchase paidPurchaseByLocator (@PathVariable String locator) {
        Purchase existingPurchase = getPurchaseByLocator(locator);
        if (existingPurchase.getOrderStatus() == 100) {
            existingPurchase.setOrderStatus(101L);
            existingPurchase.setPaymentDate(LocalDate.now());
        }
//        BeanUtils.copyProperties(purchase, existingPurchase, "id");
        return purchaseRepo.saveAndFlush(existingPurchase);
    }

}
