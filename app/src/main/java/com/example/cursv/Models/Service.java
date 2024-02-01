package com.example.cursv.Models;

public class Service {
    private int IdService;
    private String NameService;
    private double CostService;
    private String Doctor;

    public Service(int idService, String nameService, double costService, String doctor) {
        IdService = idService;
        NameService = nameService;
        CostService = costService;
        Doctor = doctor;
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
