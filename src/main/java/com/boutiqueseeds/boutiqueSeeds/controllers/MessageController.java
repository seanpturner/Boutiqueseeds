package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Message;
import com.boutiqueseeds.boutiqueSeeds.entities.User;
import com.boutiqueseeds.boutiqueSeeds.repos.MessageRepo;
import com.boutiqueseeds.boutiqueSeeds.repos.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UserRepo userRepo;

    @GetMapping
    public List<Message> messageList() {
        return messageRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Message getMessageById(@PathVariable Long id) {
//        return messageRepo.getOne(id);
        List<Message> existingMessages = messageRepo.findAll();
        for (Message m : existingMessages) {
            if (m.getId() == id) {
                return m;
            }
        }
        throw new ResponseStatusException(NOT_FOUND, "Unable to find this message");
    }

    @GetMapping
    @RequestMapping("/user/{userName}")
    public List<Message> getMessagesByUserName(@PathVariable String userName) {
        List<User> userList = userRepo.findAll();
        List<Message> messageList = messageRepo.findAll();
        List<Message> userMessageList = new ArrayList<>();
        Long userId = null;
        for (User u : userList) {
            if (u.getUserName().equalsIgnoreCase(userName)) {
                userId = u.getId();
            }
        }
        for (Message m : messageList) {
            if (m.getSenderId() == userId || m.getReceiverId() == userId) {
                userMessageList.add(m);
            }
        }
        return userMessageList;
    }

    @GetMapping
    @RequestMapping("/count/{userName}")
    public Integer getMessageCountByUserName(@PathVariable String userName) {
        List<User> userList = userRepo.findAll();
        List<Message> messageList = messageRepo.findAll();
        List<Message> userMessageList = new ArrayList<>();
        Long userId = null;
        for (User u : userList) {
            if (u.getUserName().equalsIgnoreCase(userName)) {
                userId = u.getId();
            }
        }
        for (Message m : messageList) {
            if ((m.getReceiverId() == userId) && (m.getRead() == null || m.getRead() == false)) {
                userMessageList.add(m);
            }
        }
        return userMessageList.toArray().length;
    }

    @GetMapping
    @RequestMapping("/receiver/{userId}")
    public List<Message> getAllMessagesByReceiver(@PathVariable Long userId) {
        List<Message> messageList = messageRepo.findAll();
        List<Message> userMessageList = new ArrayList<>();
        for (Message m : messageList) {
            if (m.getReceiverId().equals(userId)) {
                userMessageList.add(m);
            }
        }
        return userMessageList;
    }

    @GetMapping
    @RequestMapping("/sender/{userId}")
    public List<Message> getAllMessagesBySender(@PathVariable Long userId) {
        List<Message> messageList = messageRepo.findAll();
        List<Message> userMessageList = new ArrayList<>();
        for (Message m : messageList) {
            if (m.getSenderId().equals(userId)) {
                userMessageList.add(m);
            }
        }
        return userMessageList;
    }

    @GetMapping
    @RequestMapping("/receiver/unarchived/{userId}")
    public List<Message> getUnarchivedMessagesByReceiver(@PathVariable Long userId) {
        List<Message> messageList = messageRepo.findAll();
        List<Message> userMessageList = new ArrayList<>();
        for (Message m : messageList) {
            if (m.getReceiverId().equals(userId) && m.getArchived() == false ) {
                userMessageList.add(m);
            }
        }
        return userMessageList;
    }

    @GetMapping
    @RequestMapping("/correspondence/{user1}/{user2}")
    public List<Message> getMessagesBetweenUsers(@PathVariable Long user1, @PathVariable Long user2) {
        List<Message> messageList = messageRepo.findAll();
        List<Message> userMessageList = new ArrayList<>();
        for (Message m : messageList) {
            if ((m.getSenderId().equals(user1) && m.getReceiverId().equals(user2)) || (m.getReceiverId().equals(user1) && m.getSenderId().equals(user2)) ) {
                userMessageList.add(m);
            }
        }
        return userMessageList;
    }

    @PostMapping
    public ResponseEntity addMessage(@RequestBody final Message message) {
        message.setTimestampSent(LocalDateTime.now());
        messageRepo.saveAndFlush(message);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Message updateMessage(@PathVariable Long id, @RequestBody Message message) {
        if (id == message.getId()) {
            List<Message> existingMessages = messageRepo.findAll();
            for (Message m: existingMessages) {
                if (m.getId() == message.getId()) {
                    BeanUtils.copyProperties(message, m, "id");
                    return messageRepo.saveAndFlush(m);
                }
            }
        }
        throw new ResponseStatusException(BAD_REQUEST, "Unable to update this message");
    }

    @RequestMapping(value = "/updateRead/{id}", method = RequestMethod.PUT)
    public Message updateRead(@PathVariable Long id) {
        List<Message> existingMessages = messageRepo.findAll();
        for (Message m : existingMessages) {
            if (m.getId() == id) {
                m.setRead(true);
                if (m.getTimestampOpened() == null) {
                    m.setTimestampOpened(LocalDateTime.now());
                }
                messageRepo.saveAndFlush(m);
            }
        }
        throw new ResponseStatusException(BAD_REQUEST, "Unable to update this message");
    }
    @RequestMapping(value = "/updateArchived/{id}", method = RequestMethod.PUT)
    public Message updateArchived(@PathVariable Long id) {
        List<Message> existingMessages = messageRepo.findAll();
        for (Message m : existingMessages) {
            if (m.getId() == id) {
                m.setArchived(true);
                if (m.getTimestampArchived() == null) {
                    m.setTimestampArchived(LocalDateTime.now());
                }
                messageRepo.saveAndFlush(m);
            }
        }
        throw new ResponseStatusException(BAD_REQUEST, "Unable to update this message");
    }


}
