package ru.egorov.dto;

public class MinAndMaxExpensesCriteria implements Criteria {

    private double minExpenses;
    private double maxExpenses;

    public MinAndMaxExpensesCriteria(double minExpenses, double maxExpenses) {
        this.minExpenses = minExpenses;
        this.maxExpenses = maxExpenses;
    }

    public MinAndMaxExpensesCriteria() {
    }

    public double getMinExpenses() {
        return minExpenses;
    }

    public void setMinExpenses(double minExpenses) {
        this.minExpenses = minExpenses;
    }

    public double getMaxExpenses() {
        return maxExpenses;
    }

    public void setMaxExpenses(double maxExpenses) {
        this.maxExpenses = maxExpenses;
    }
}
