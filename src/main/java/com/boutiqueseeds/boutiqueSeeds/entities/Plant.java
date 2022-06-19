package com.boutiqueseeds.boutiqueSeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "plants")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String[] notes;
    private Integer maternalLine;
    private Integer paternalLine;
    private String sex;
    private Boolean clone;
    private String[] imageList;



    public Plant() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getNotes() {
        return notes;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }

    public Integer getMaternalLine() {
        return maternalLine;
    }

    public void setMaternalLine(Integer maternalLine) {
        this.maternalLine = maternalLine;
    }

    public Integer getPaternalLine() {
        return paternalLine;
    }

    public void setPaternalLine(Integer paternalLine) {
        this.paternalLine = paternalLine;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Boolean getClone() {
        return clone;
    }

    public void setClone(Boolean clone) {
        this.clone = clone;
    }

    public String[] getImageList() {
        return imageList;
    }

    public void setImageList(String[] imageList) {
        this.imageList = imageList;
    }
}
