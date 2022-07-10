package com.boutiqueseeds.boutiqueSeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "seeds")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Seed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String[] notes;
    private Long mother;
    private Long father;
    private Integer quantityAvailable;
    private Integer maternalLine;
    private Integer paternalLine;
    private Boolean feminized;
    private Boolean autoFlower;
    private Boolean active;
    private BigDecimal price;
    private String storageId;

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

    public Long getMother() {
        return mother;
    }

    public void setMother(Long mother) {
        this.mother = mother;
    }

    public Long getFather() {
        return father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
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

    public Boolean getFeminized() {
        return feminized;
    }

    public void setFeminized(Boolean feminized) {
        this.feminized = feminized;
    }

    public Boolean getAutoFlower() {
        return autoFlower;
    }

    public void setAutoFlower(Boolean autoFlower) {
        this.autoFlower = autoFlower;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }
}
