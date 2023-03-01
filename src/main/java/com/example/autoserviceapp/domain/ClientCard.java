package com.example.autoserviceapp.domain;

/**
 * Client Card class
 */
public class ClientCard extends Entity {
    String firstName, lastName, cnp;

    /**
     * Default constructor
     */
    public ClientCard() {
        this.firstName = "";
        this.lastName = "";
    }

    /**
     * Constructor with parameters
     * @param firstName String
     * @param lastName String
     * @param cnp String
     */
    public ClientCard(String firstName, String lastName, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
    }

    /**
     * Getter for first name
     * @return String
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter for first name
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for last name
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter for last name
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for CNP
     * @return String
     */
    public String getCnp() {
        return this.cnp;
    }

    /**
     * Setter for CNP
     * @param cnp String
     */
    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cnp='" + cnp + '\'' +
                '}';
    }
}
