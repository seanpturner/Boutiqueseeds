package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Message;
import com.boutiqueseeds.boutiqueSeeds.repos.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageRepo messageRepo;

    @GetMapping
    public List<Message> messageList() {
        return messageRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Message getMessageById(@PathVariable Long id) {
        return messageRepo.getOne(id);
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
            if ((m.getSenderId().equals(user1) && m.getReceiverId() .equals(user2)) || (m.getReceiverId().equals(user1) && m.getSenderId() .equals(user2)) ) {
                userMessageList.add(m);
            }
        }
        return userMessageList;
    }

    @PostMapping
    public ResponseEntity addMessage(@RequestBody final Message message) {
        messageRepo.saveAndFlush(message);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Message updateMessage(@PathVariable Long id, @RequestBody Message message) {
        Message existingMessage = messageRepo.getOne(id);
        BeanUtils.copyProperties(message, existingMessage, "id");
        return messageRepo.saveAndFlush(existingMessage);
    }
}
