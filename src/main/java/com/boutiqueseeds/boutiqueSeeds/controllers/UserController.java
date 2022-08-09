package com.boutiqueseeds.boutiqueSeeds.controllers;


import com.boutiqueseeds.boutiqueSeeds.entities.User;
import com.boutiqueseeds.boutiqueSeeds.repos.UserRepo;
import com.boutiqueseeds.boutiqueSeeds.services.Secure;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @CrossOrigin(origins="*")
    @GetMapping
    public List<User> userList() {
        return userRepo.findAll();
    }

    @CrossOrigin(origins="*")
    @GetMapping
    @RequestMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return userRepo.getOne(id);
    }

    @CrossOrigin(origins="*")
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

    @CrossOrigin(origins="*")
    @GetMapping
    @RequestMapping("/getToken/{userName}/{password}")
    public String grantAccessToken(@PathVariable String userName, @PathVariable String password) throws NoSuchAlgorithmException {
        String token = "";
        User thisUser = getUserByName(userName);
            if (thisUser != null) {
                LocalDate today = LocalDate.now();
                String dbpw = thisUser.getPassword();
                if (password.equals(dbpw)) {
                    String dbfn = thisUser.getfName();
                    String dbln = thisUser.getlName();
                    String dbun = thisUser.getUserName();
                    String tokenInfo = dbun + dbln + dbfn + dbpw + today;
                    token = Secure.Hash(tokenInfo);
                }
            }else{
                token = "invalid";
            }
        return token;
    }

    @CrossOrigin(origins="*")
    @GetMapping
    @RequestMapping("/checkCredentials/{userName}")
    public Boolean checkAccess(@PathVariable String userName, @RequestHeader("bearerToken") String bearerToken ) throws NoSuchAlgorithmException {

        Boolean grantAccess = false;
        User thisUser = getUserByName(userName);
        if (thisUser != null) {
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);
            String dbpw = thisUser.getPassword();
            String dbfn = thisUser.getfName();
            String dbln = thisUser.getlName();
            String dbun = thisUser.getUserName();
            String tokenInfo1 = Secure.Hash(dbun + dbln + dbfn + dbpw + today) ;
            String tokenInfo2 = Secure.Hash(dbun + dbln + dbfn + dbpw + yesterday) ;
            if (bearerToken.equals(tokenInfo1) || bearerToken.equals(tokenInfo2)) {
                grantAccess = true;
            }
        }
        return grantAccess;
    }

    @CrossOrigin(origins="*")
    @GetMapping
    @RequestMapping("/checkUserLevel/{userName}")
    public Object checkLevel(@PathVariable String userName, @RequestHeader("bearerToken") String bearerToken ) throws NoSuchAlgorithmException {
        class AuthObject {
            private Boolean grantAccess;
            private String userAccountType;

            public Boolean getGrantAccess() {
                return grantAccess;
            }

            public void setGrantAccess(Boolean grantAccess) {
                this.grantAccess = grantAccess;
            }

            public String getUserAccountType() {
                return userAccountType;
            }

            public void setUserAccountType(String userAccountType) {
                this.userAccountType = userAccountType;
            }
        }

        AuthObject userAuth = new AuthObject();
        userAuth.setGrantAccess(false);
        userAuth.setUserAccountType(null);

        User thisUser = getUserByName(userName);
        if (thisUser != null) {
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);
            String dbpw = thisUser.getPassword();
            String dbfn = thisUser.getfName();
            String dbln = thisUser.getlName();
            String dbun = thisUser.getUserName();
            String dbat = thisUser.getAccountType();
            String tokenInfo1 = Secure.Hash(dbun + dbln + dbfn + dbpw + today) ;
            String tokenInfo2 = Secure.Hash(dbun + dbln + dbfn + dbpw + yesterday) ;
            if (bearerToken.equals(tokenInfo1) || bearerToken.equals(tokenInfo2)) {
                userAuth.setGrantAccess(true);
                userAuth.setUserAccountType(dbat);
            }
        }
        return userAuth;
    }

    @CrossOrigin(origins="*")
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

    @CrossOrigin(origins="*")
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
    @CrossOrigin(origins="*")
    @RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepo.getOne(id);
        BeanUtils.copyProperties(user, existingUser, "id");
        return userRepo.saveAndFlush(existingUser);
    }

    //update everything and rehashes the password
    @CrossOrigin(origins="*")
    @RequestMapping(value = "/updateProfile/{id}", method = RequestMethod.PUT)
    public User updateProfile(@PathVariable Long id, @RequestBody User user) throws NoSuchAlgorithmException {
        User existingUser = userRepo.getOne(id);
        BeanUtils.copyProperties(user, existingUser, "id");
        String existingPassword = getUser(id).getPassword();
            String hashedPassword = Secure.Hash(user.getPassword());
            existingUser.setPassword(hashedPassword);
        return userRepo.saveAndFlush(existingUser);
    }

    @CrossOrigin(origins="*")
    @RequestMapping(value = "updatepasswordonly/{id}/{password}", method = RequestMethod.PUT)
    public User updatePasswordOnly(@PathVariable Long id, @PathVariable String password) throws NoSuchAlgorithmException {
        User existingUser = userRepo.getOne(id);
        BeanUtils.copyProperties(existingUser, existingUser, "id");
        String hashedPassword = Secure.Hash(password);
        existingUser.setPassword(hashedPassword);
        return userRepo.saveAndFlush(existingUser);
    }

}
