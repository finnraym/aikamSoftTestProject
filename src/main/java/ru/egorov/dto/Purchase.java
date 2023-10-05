package ru.egorov.dto;

public class Purchase {
    private String name;
    private double expenses;

    public Purchase(String name, double expenses) {
        this.name = name;
        this.expenses = expenses;
    }

    public Purchase() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
}
