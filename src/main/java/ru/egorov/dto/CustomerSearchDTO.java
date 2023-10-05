package ru.egorov.dto;

public class CustomerSearchDTO {
    private String lastName;
    private String firstName;

    public CustomerSearchDTO(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public CustomerSearchDTO() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
