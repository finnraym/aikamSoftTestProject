package ru.egorov.dto;

public class LastNameCriteria implements Criteria {

    private String lastName;

    public LastNameCriteria(String lastName) {
        this.lastName = lastName;
    }

    public LastNameCriteria() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
