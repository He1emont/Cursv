package com.example.cursv.Models;

import com.example.cursv.DatabaseUtils;

import java.io.Serializable;

public class Service implements Serializable {
    private int IdService;
    private String NameService;
    private double CostService;
    private int IdDoctor;
    private int IdType;
    private int DurationMin;

    public Service(int idService, String nameService, double costService, int idDoctor, int idType, int durationMin) {
        IdService = idService;
        NameService = nameService;
        CostService = costService;
        IdDoctor = idDoctor;
        IdType = idType;
        DurationMin = durationMin;
    }

    public int getDurationMin() {
        return DurationMin;
    }

    public void setDurationMin(int durationMin) {
        DurationMin = durationMin;
    }

    public int getIdType() {
        return IdType;
    }

    public void setIdType(int idType) {
        IdType = idType;
    }

    public int getIdService() {
        return IdService;
    }

    public void setIdService(int idService) {
        IdService = idService;
    }

    public String getNameService() {
        return NameService;
    }

    public void setNameService(String nameService) {
        NameService = nameService;
    }

    public double getCostService() {
        return CostService;
    }

    public void setCostService(double costService) {
        CostService = costService;
    }

    public int getIdDoctor() {
        return IdDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        IdDoctor = idDoctor;
    }

}
