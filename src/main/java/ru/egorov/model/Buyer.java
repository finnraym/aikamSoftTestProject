package ru.egorov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Buyer {
    private long id;
    private String firstName;
    private String lastName;
    private List<Purchase> purchases;
    private double totalExpenses;

    public Buyer() {
        purchases = new ArrayList<>();
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void addExpenses(double value) {
        totalExpenses += value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return id == buyer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
