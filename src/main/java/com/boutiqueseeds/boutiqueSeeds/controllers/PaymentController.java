package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Payment;
import com.boutiqueseeds.boutiqueSeeds.repos.PaymentRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepo paymentRepo;

    @GetMapping
    public List<Payment> paymentList() {
        return paymentRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Payment getPayment(@PathVariable Long id) {
        return paymentRepo.getOne(id);
    }

    @PostMapping
    public ResponseEntity addPayment(@RequestBody final Payment payment) {
        List<Payment> allPayments = paymentRepo.findAll();
        for (Payment p : allPayments) {
            if (p.getType().equals(payment.getType())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if (p.getId().equals(payment.getId())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        paymentRepo.saveAndFlush(payment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        Payment existingPayment = paymentRepo.getOne(id);
        BeanUtils.copyProperties(payment, existingPayment, "id");
        return paymentRepo.saveAndFlush(existingPayment);
    }

}
