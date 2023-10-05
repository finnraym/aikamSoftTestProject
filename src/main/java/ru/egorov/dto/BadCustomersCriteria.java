package ru.egorov.dto;

public class BadCustomersCriteria implements Criteria {

    private int badCustomers;

    public BadCustomersCriteria(int badCustomers) {
        this.badCustomers = badCustomers;
    }

    public BadCustomersCriteria() {
    }

    public int getBadCustomers() {
        return badCustomers;
    }

    public void setBadCustomers(int badCustomers) {
        this.badCustomers = badCustomers;
    }
}
