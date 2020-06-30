package com.server.restaurantrent.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String uuid;
    private long idUser;

    public AuthToken() {
    }

    public AuthToken(String uuid, long idUser) {
        this.uuid = uuid;
        this.idUser = idUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}
