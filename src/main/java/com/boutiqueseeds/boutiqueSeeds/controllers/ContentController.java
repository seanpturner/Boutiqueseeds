package com.boutiqueseeds.boutiqueSeeds.controllers;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.boutiqueseeds.boutiqueSeeds.entities.Content;
import com.boutiqueseeds.boutiqueSeeds.repos.ContentRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentRepo contentRepo;

    @GetMapping
    public List<Content> contentList() {
        return contentRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Content getContent(@PathVariable Long id) {
        return contentRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/page/{pageName}")
    public List<Content> getContentByPageName(@PathVariable String pageName) {
        List<Content> contentList = contentRepo.findAll();
        List<Content> allPageContent = new ArrayList<>();
        for (Content c: contentList) {
            if (c.getPageName().toLowerCase().equals(pageName.toLowerCase())) {
                allPageContent.add(c);
            }
        }
        return allPageContent;
    }

    @GetMapping
    @RequestMapping("/current")
    public List<Content> getCurrentContent() {
        List<Content> allContent = contentRepo.findAll();
        List<Content> currentContent = new ArrayList<>();
        for (Content c : allContent) {
            if (c.getStartDate() != null && c.getEndDate() != null) {
                LocalDate startDate = c.getStartDate();
                LocalDate endDate = c.getEndDate();
                LocalDate today = LocalDate.now();
                if ((startDate.isBefore(today) || startDate.isEqual(today)) && endDate.isAfter(today)) {
                    currentContent.add(c);
                }
            }
        }
        for (Content c : allContent) {
            if (c.getDefaultContent() == true) {
                Boolean addPage = true;
                for (Content current : currentContent) {
                    if (c.getPageName().equals(current.getPageName())) {
                        addPage = false;
                    }
                }
                if (addPage == true) {
                    currentContent.add(c);
                }
            }
        }
        return currentContent;
    }

    @PostMapping
    public ResponseEntity addContent(@RequestBody final Content content) {
        if(content.getStartDate() != null) {
            LocalDate sd = LocalDate.parse(String.valueOf(content.getStartDate()));
            content.setStartDate(sd);
        }
        if (content.getEndDate() != null) {
            LocalDate ed = LocalDate.parse(String.valueOf(content.getEndDate()));
            content.setEndDate(ed);
        }
        LocalDateTime now = LocalDateTime.now();
        content.setUpdatedDate(now);
        contentRepo.saveAndFlush(content);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Content updateContent(@PathVariable Long id, @RequestBody Content content) {
        Content existingContent = contentRepo.getOne(id);
        BeanUtils.copyProperties(content, existingContent, "id");
        return contentRepo.saveAndFlush(existingContent);
    }
}
