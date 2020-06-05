package com.server.restaurantrent.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String password;
    private Boolean isAuth = false;

    public Owner() {
    }

    public Owner(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Email: " + email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAuth() {
        return isAuth;
    }

    public void setAuth(Boolean auth) {
        isAuth = auth;
    }
}
