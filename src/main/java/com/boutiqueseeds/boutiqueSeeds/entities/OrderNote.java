package com.boutiqueseeds.boutiqueSeeds.entities;

import java.time.LocalDate;

public class OrderNote {

    private LocalDate date;

    private String note;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
