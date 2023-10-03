package ru.egorov.service;

import ru.egorov.model.Buyer;

import java.util.List;

public class StatResponse extends Response {
    private int totalDays;
    private List<Buyer> buyers;
    private double totalExpenses;
    private double avgExpenses;

    public StatResponse() {
        super("stat");
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Buyer> buyers) {
        this.buyers = buyers;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void addTotalExpenses(double value) {
        totalExpenses += value;
    }

    public Double getAvgExpenses() {
        return avgExpenses;
    }

    public void setAvgExpenses(Double avgExpenses) {
        this.avgExpenses = avgExpenses;
    }
}
