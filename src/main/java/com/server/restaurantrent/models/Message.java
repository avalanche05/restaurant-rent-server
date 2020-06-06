package com.server.restaurantrent.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long idRent;
    private Boolean isOwner;
    private String textMessage;

    public Message() {
    }

    public Message(long idRent, boolean isOwner, String textMessage) {
        this.idRent = idRent;
        this.isOwner = Boolean.getBoolean(Boolean.toString(isOwner));
        this.textMessage = textMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdRent() {
        return idRent;
    }

    public void setIdRent(long idRent) {
        this.idRent = idRent;
    }

    public Boolean isOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
