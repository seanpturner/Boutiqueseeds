package com.boutiqueseeds.boutiqueSeeds.controllers;

import com.boutiqueseeds.boutiqueSeeds.entities.Line;
import com.boutiqueseeds.boutiqueSeeds.repos.LineRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lines")
public class LineController {
    @Autowired LineRepo lineRepo;

    @GetMapping
    public List<Line> lineList() {
        return lineRepo.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Line getLine(@PathVariable Long id) {
        return lineRepo.getOne(id);
    }

    @GetMapping
    @RequestMapping("/line/{lineName}")
    public Line getLineByName(@PathVariable String lineName) {
        List<Line> lineList = lineRepo.findAll();
        for (Line l : lineList) {
            if (l.getName().equalsIgnoreCase(lineName)) {
                return l;
            }
        }
        return null;
    }

    @PostMapping
    public ResponseEntity addLine(@RequestBody final Line line) throws Exception {
        List<Line> allLines = lineRepo.findAll();
        for (Line l : allLines) {
            if (l.getName().equalsIgnoreCase(line.getName())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if (l.getId() == line.getId()) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        lineRepo.saveAndFlush(line);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void removeLine(@PathVariable Long id) {
        lineRepo.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Line updateLine(@PathVariable Long id, @RequestBody Line line) {
        Line existingLine = lineRepo.getOne(id);
        BeanUtils.copyProperties(line, existingLine, "id");
        return lineRepo.saveAndFlush(existingLine);
    }

}
