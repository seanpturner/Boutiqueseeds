package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Comment;
import com.boutiqueseeds.boutiqueSeeds.repos.CommentRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentRepo commentRepo;

    @GetMapping
    public List<Comment> commentList() {
        return commentRepo.findAll();
    }

    @GetMapping
    @RequestMapping("/seed/{seedId}")
    public List<Comment> getCommentBySeed(@PathVariable Long seedId) {
        List<Comment> commentList = commentRepo.findAll();
        List<Comment> seedComments = new ArrayList<>();
        for (Comment c : commentList) {
            if (c.getSeedId().equals(seedId)) {
                seedComments.add(c);
            }
        }
        return seedComments;
    }

    @GetMapping
    @RequestMapping("{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/approved/seed/{seedId}")
    public List<Comment> getApprovedCommentBySeed(@PathVariable Long seedId) {
        List<Comment> commentList = commentRepo.findAll();
        List<Comment> approvedSeedComments = new ArrayList<>();
        for (Comment c : commentList) {
            if (c.getApproved() != null) {
                if (c.getSeedId().equals(seedId) && c.getApproved() == true) {
                    approvedSeedComments.add(c);
                }
            }
        }
        return approvedSeedComments;
//        ToDo: if I add an id that is no good, I get a 500. How do I fix this?
    }

    @GetMapping
    @RequestMapping("/unapproved")
    public List<Comment> getPendingComments() {
        List<Comment> commentList = commentRepo.findAll();
        List<Comment> pendingSeedComments = new ArrayList<>();
        for (Comment c : commentList) {
            if ( c.getApproved() == null) {
                pendingSeedComments.add(c);
            }
        }
        return pendingSeedComments;
    }

    @GetMapping
    @RequestMapping("/blocked/seed/{seedId}")
    public List<Comment> getBlockedCommentBySeed(@PathVariable Long seedId) {
        List<Comment> commentList = commentRepo.findAll();
        List<Comment> approvedSeedComments = new ArrayList<>();
        for (Comment c : commentList) {
            if (c.getSeedId().equals(seedId) && c.getApproved() == false) {
                approvedSeedComments.add(c);
            }
        }
        return approvedSeedComments;
    }

    @GetMapping
    @RequestMapping("/blocked")
    public List<Comment> getAllBlockedComments() {
        List<Comment> commentList = commentRepo.findAll();
        List<Comment> blockedComments = new ArrayList<>();
        for (Comment c : commentList) {
            if (c.getApproved() != null) {
                if (c.getApproved() == false) {
                    blockedComments.add(c);
                }
            }
        }
        return blockedComments;
    }
    @GetMapping
    @RequestMapping("/user/{userId}")
    public List<Comment> getCommentsByUser(@PathVariable Long userId) {
        List<Comment> commentList = commentRepo.findAll();
        List<Comment> userComments = new ArrayList<>();
        for (Comment c : commentList) {
            if (c.getUserId().equals(userId)) {
                userComments.add(c);
            }
        }
        return userComments;
    }

    @PostMapping
    public ResponseEntity addComment(@RequestBody final Comment comment) {
        commentRepo.saveAndFlush(comment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        Comment existingComment = commentRepo.getOne(id);
        BeanUtils.copyProperties(comment, existingComment, "id");
        return commentRepo.saveAndFlush(existingComment);
    }

    @RequestMapping(value = "/like/{commentId}/{userId}", method = RequestMethod.PUT)
    public Comment updateLike(@PathVariable Long commentId, @PathVariable Long userId) {
        Comment existingComment = commentRepo.getOne(commentId);
        Long[] currentLikes = existingComment.getLikes();
        List<Long> updatedLikes = new ArrayList<>();
        Boolean addLikeToArray = true;
        for (Long l : currentLikes) {
            if (l.equals(userId)) {
                addLikeToArray = false;
            }
            if (!l.equals(userId)) {
                updatedLikes.add(l);
            }
        }
        if (addLikeToArray == true) {
            updatedLikes.add(userId);
        }
        existingComment.setLikes(updatedLikes.toArray(new Long[0]));
        return commentRepo.saveAndFlush(existingComment);
    }
}

