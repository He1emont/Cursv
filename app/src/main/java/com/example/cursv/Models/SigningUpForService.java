package com.example.cursv.Models;

public class SigningUpForService {
    private int id;
    private String date;
    private int idService;
    private int idHuman;

    public SigningUpForService(int id, String date, int idService, int idHuman) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
}
