package com.example.cursv.Models;

import java.time.LocalDateTime;

public class SigningUpForService {
    private long id;
    private LocalDateTime date;
    private int idService;
    private int idHuman;

    public SigningUpForService(long id, LocalDateTime date, int idService, int idHuman) {
        this.id = id;
        this.date = date;
        this.idService = idService;
        this.idHuman = idHuman;
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

    public void setId(int id) {
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
