package com.example.cursv.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SigningUpForService implements Serializable {
    private long id;
    private LocalDateTime date;
    private int idService;
    private int idHuman;
    private String address;

    public SigningUpForService(long id, LocalDateTime date, int idService, int idHuman, String address) {
        this.id = id;
        this.date = date;
        this.idService = idService;
        this.idHuman = idHuman;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdHuman() {
        return idHuman;
    }

    public void setIdHuman(int idHuman) {
        this.idHuman = idHuman;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
}
