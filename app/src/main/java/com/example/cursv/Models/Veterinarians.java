package com.example.cursv.Models;

public class Veterinarians {
    private int id;
    private String fio;
    private int experience;

    public Veterinarians(int id, String fio, int experience) {
        this.id = id;
        this.fio = fio;
        this.experience = experience;
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
