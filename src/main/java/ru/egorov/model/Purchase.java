package ru.egorov.model;

import java.time.LocalDate;

public class Purchase {
    private String productName;
    private Double expenses;

    public Purchase() {
    }

    public Purchase(String productName, Double expenses) {
        this.productName = productName;
        this.expenses = expenses;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
}
