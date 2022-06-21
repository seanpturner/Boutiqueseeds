package com.boutiqueseeds.boutiqueSeeds.controllers;


import com.boutiqueseeds.boutiqueSeeds.entities.User;
import com.boutiqueseeds.boutiqueSeeds.repos.UserRepo;
import com.boutiqueseeds.boutiqueSeeds.services.Secure;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<User> userList() {
        return userRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return userRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/user/{userName}")
    public User getUserByName(@PathVariable String userName) {
        List<User> userList = userRepo.findAll();
        for (User u : userList) {
            if (u.getUserName().equalsIgnoreCase(userName)) {
                return u;
            }
        }
        return null;
    }

    @GetMapping
    @RequestMapping("/active")
    public List<User> getActiveUsers() {
        List<User> users = userRepo.findAll();
        List<User> activeUsers = new ArrayList<>();
        for (User u : users) {
            if (u.getActive().equals(true)) {
                activeUsers.add(u);
            }
        }
        return activeUsers;
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody final User user) throws Exception {
        List<User> allUsers = userRepo.findAll();
        for (User u : allUsers) {
            if (u.getUserName().equalsIgnoreCase(user.getUserName())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if (u.getId() == user.getId()) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        String hashedPassword = Secure.Hash(user.getPassword());
        user.setPassword(hashedPassword);
        userRepo.saveAndFlush(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    //  Update everything except the password so it doesn't re-hash
    @RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepo.getOne(id);
        BeanUtils.copyProperties(user, existingUser, "id");
        return userRepo.saveAndFlush(existingUser);
    }

    //update everything and rehashes the password
    @RequestMapping(value = "/updateProfile/{id}", method = RequestMethod.PUT)
    public User updateProfile(@PathVariable Long id, @RequestBody User user) throws NoSuchAlgorithmException {
        User existingUser = userRepo.getOne(id);
        BeanUtils.copyProperties(user, existingUser, "id");
        String existingPassword = getUser(id).getPassword();
            String hashedPassword = Secure.Hash(user.getPassword());
            existingUser.setPassword(hashedPassword);
        return userRepo.saveAndFlush(existingUser);
    }

    @RequestMapping(value = "updatepasswordonly/{id}/{password}", method = RequestMethod.PUT)
    public User updatePasswordOnly(@PathVariable Long id, @PathVariable String password) throws NoSuchAlgorithmException {
        User existingUser = userRepo.getOne(id);
        String hashedPassword = Secure.Hash(password);
        existingUser.setPassword(hashedPassword);
        return existingUser;
    }

}
