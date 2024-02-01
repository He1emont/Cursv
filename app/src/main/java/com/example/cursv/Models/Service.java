package com.example.cursv.Models;

public class Service {
    private int IdService;
    private String NameService;
    private double CostService;
    private String Doctor;
    private int IdType;

    public Service(int idService, String nameService, double costService, String doctor, int idType) {
        IdService = idService;
        NameService = nameService;
        CostService = costService;
        Doctor = doctor;
        IdType = idType;
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

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }
}
