package ru.egorov.dto;

import java.util.ArrayList;
import java.util.List;

public class StatResponse implements Response {
    private final String type;
    private int totalDays;
    private final List<CustomerStatDTO> customers;
    private double totalExpenses;
    private double avgExpenses;

    public StatResponse() {
        type = "stat";
        customers = new ArrayList<>();
    }

    public StatResponse(int totalDays, double totalExpenses, double avgExpenses) {
        this();
        this.totalDays = totalDays;
        this.totalExpenses = totalExpenses;
        this.avgExpenses = avgExpenses;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public List<CustomerStatDTO> getCustomers() {
        return customers;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public double getAvgExpenses() {
        return avgExpenses;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void addExpenses(double expenses) {
        totalExpenses += expenses;
    }

    public void setAvgExpenses(double avgExpenses) {
        this.avgExpenses = avgExpenses;
    }

    public void addCustomer(CustomerStatDTO customer) {
        customers.add(customer);
    }
    @Override
    public String getType() {
        return type;
    }
}
