package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Discount;
import com.boutiqueseeds.boutiqueSeeds.repos.DiscountRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    @Autowired
    DiscountRepo discountRepo;

    @GetMapping
    public List<Discount> discountList() {
        return discountRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Discount getDiscount(@PathVariable Long id) {
        return discountRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/code/{code}")
    public Discount getDiscountByCode(@PathVariable String code) {
        List<Discount> discountList = discountRepo.findAll();
        for (Discount d : discountList) {
            if (d.getDiscountCode().toLowerCase().equals(code.toLowerCase())) {
                return d;
            }
        }
        return null;
    }

    @GetMapping
    @RequestMapping("/userSpecific/all")
    public List<Discount> getAllUserDiscounts() {
        List<Discount> discountList = discountRepo.findAll();
        List<Discount> userDiscountList = new ArrayList<>();
        for (Discount d : discountList) {
            if (d.getCustomerSpecific() == true) {
                userDiscountList.add(d);
            }
        }
        return userDiscountList;
    }

    @GetMapping
    @RequestMapping("/userSpecific/available")
    public List<Discount> getAvailableUserDiscounts() {
        List<Discount> discountList = discountRepo.findAll();
        List<Discount> userDiscountList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Discount d : discountList) {
            if (d.getCustomerSpecific() == true && (d.getStartDate().isBefore(today) || d.getStartDate().isEqual(today)) && d.getEndDate().isAfter(today)) {
                userDiscountList.add(d);
            }
        }
        return userDiscountList;
    }

    @GetMapping
    @RequestMapping("/userSpecific/all/{id}")
    public List<Discount> getAllDiscountsByUser(@PathVariable Long id) {
        List<Discount> discountList = discountRepo.findAll();
        List<Discount> userDiscountList = new ArrayList<>();
        for (Discount d : discountList) {
            if (d.getCustomerId() != null) {
                if (d.getCustomerId().equals(id)) {
                    userDiscountList.add(d);
                }
            }
        }
        return userDiscountList;
    }

    @GetMapping
    @RequestMapping("/userSpecific/available/{id}")
    public List<Discount> getActiveDiscountsByUser(@PathVariable Long id) {
        List<Discount> discountList = discountRepo.findAll();
        List<Discount> userDiscountList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Discount d : discountList) {
            if (d.getCustomerId() != null) {
                if (d.getCustomerId().equals(id) && (d.getStartDate().isBefore(today) || d.getStartDate().isEqual(today)) && d.getEndDate().isAfter(today)) {
                    userDiscountList.add(d);
                }
            }
        }
        return userDiscountList;
    }

    @PostMapping
    public ResponseEntity addDiscount(@RequestBody final Discount discount) {
        discountRepo.saveAndFlush(discount);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Discount updateDiscount(@PathVariable Long id, @RequestBody Discount discount) {
        Discount existingDiscount = discountRepo.getOne(id);
        BeanUtils.copyProperties(discount, existingDiscount, "id");
        return discountRepo.saveAndFlush(existingDiscount);
    }

}
