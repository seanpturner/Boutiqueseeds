package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Login;
import com.boutiqueseeds.boutiqueSeeds.repos.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logins")
public class LoginController {
    @Autowired
    private LoginRepo loginRepo;

    @GetMapping
    public List<Login> loginList() {
        return loginRepo.findAll();
    }

    @GetMapping
    @RequestMapping("/user/{userId}")
    public List<Login> getLoginsByUserId(@PathVariable Integer userId) {
        List<Login> loginList = loginRepo.findAll();
        List<Login> userList = new ArrayList<>();
        for (Login l : loginList) {
            Integer uId = l.getUserId();
            if (uId.equals(userId)) {
                userList.add(l);
            }
        }
        return userList;
    }

    @GetMapping
    @RequestMapping("/since/{startDate}")
    public List<Login> getLoginsSinceDate(@PathVariable String startDate) {
        LocalDate dd = LocalDate.parse(startDate);
        LocalDateTime d = LocalDateTime.of(dd.getYear(), dd.getMonth(), dd.getDayOfMonth(),0, 0, 0 );
        List<Login> loginList = loginRepo.findAll();
        List<Login> loginsSince = new ArrayList<>();
        for (Login l : loginList) {
            LocalDateTime date = l.getTimeStamp();
            if (date != null){
                if (date.isAfter(d)) {
                    loginsSince.add(l);
                }
            }
        }
        return loginsSince;
    }

    @PostMapping
    public ResponseEntity addLogin(@RequestBody final Login login) throws Exception {
        LocalDateTime date = LocalDateTime.now();
        login.setTimeStamp(date);
        loginRepo.saveAndFlush(login);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}