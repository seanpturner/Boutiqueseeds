package com.boutiqueseeds.boutiqueSeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="Messages")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;

    private Long receiverId;

    private LocalDateTime timestampSent;

    private LocalDateTime timestampOpened;

    private LocalDateTime timestampArchived;

    @Lob
//    @Column(columnDefinition = "CLOB")
    private String messageBody;

    private String messageSubject;

    private Boolean read;

    private Boolean archived;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDateTime getTimestampSent() {
        return timestampSent;
    }

    public void setTimestampSent(LocalDateTime timestampSent) {
        this.timestampSent = timestampSent;
    }

    public LocalDateTime getTimestampOpened() {
        return timestampOpened;
    }

    public void setTimestampOpened(LocalDateTime timestampOpened) {
        this.timestampOpened = timestampOpened;
    }

    public LocalDateTime getTimestampArchived() {
        return timestampArchived;
    }

    public void setTimestampArchived(LocalDateTime timestampArchived) {
        this.timestampArchived = timestampArchived;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
