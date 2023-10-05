package ru.egorov.dto;

import java.util.ArrayList;
import java.util.List;

public class CustomerStatDTO {

    private String name;
    private final List<Purchase> purchases;
    private double totalExpenses;

    public CustomerStatDTO(String name) {
        this();
        this.name = name;
    }

    public CustomerStatDTO() {
        purchases = new ArrayList<>();
        totalExpenses = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void addExpenses(double expenses) {
        totalExpenses += expenses;
    }
}
