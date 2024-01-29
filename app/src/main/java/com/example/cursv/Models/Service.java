package com.example.cursv.Models;

public class Service {
    private int IdService;
    private String NameService;
    private double CostService;

    public Service(int idService, String nameService, double costService) {
        IdService = idService;
        NameService = nameService;
        CostService = costService;
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
}
