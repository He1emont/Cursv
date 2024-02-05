package com.example.cursv.Models;

import java.io.Serializable;

public class Veterinarians implements Serializable {
    private int id;
    private String fio;
    private int experience;
    private int idVeterinarianType;

    public Veterinarians(int id, String fio, int experience, int idVeterinarianType) {
        this.id = id;
        this.fio = fio;
        this.experience = experience;
        this.idVeterinarianType = idVeterinarianType;
    }

    public int getIdVeterinarianType() {
        return idVeterinarianType;
    }

    public void setIdVeterinarianType(int idVeterinarianType) {
        this.idVeterinarianType = idVeterinarianType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
