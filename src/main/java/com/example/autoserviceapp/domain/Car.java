package com.example.autoserviceapp.domain;

/**
 * Car class
 */
public class Car extends Entity {
    private String model;
    private int yearOfPurchase, numberOfKilometers;
    private String inGuarantee;

    /**
     * Default constructor
     */
    public Car() {
        this.model = "";
        this.yearOfPurchase = 1;
        this.numberOfKilometers = 1;
        this.inGuarantee = "true";
    }

    /**
     * Constructor with parameters
     * @param model String
     * @param yearOfPurchase Integer
     * @param numberOfKilometers Integer
     * @param inGuarantee String
     */
    public Car(String model, int yearOfPurchase, int numberOfKilometers, String inGuarantee) {
        this.model = model;
        this.yearOfPurchase = yearOfPurchase;
        this.numberOfKilometers = numberOfKilometers;
        this.inGuarantee = inGuarantee;
    }

    /**
     * Getter for model
     * @return String
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Setter for model
     * @param model String
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Getter for year of purchase
     * @return Integer
     */
    public int getYearOfPurchase() {
        return this.yearOfPurchase;
    }

    /**
     * Setter for year of purchase
     * @param yearOfPurchase Integer
     */
    public void setYearOfPurchase(int yearOfPurchase) {
        this.yearOfPurchase = yearOfPurchase;
    }

    /**
     * Getter for number of kilometers
     * @return Integer
     */
    public int getNumberOfKilometers() {
        return this.numberOfKilometers;
    }

    /**
     * Setter for number of kilometers
     * @param numberOfKilometers Integer
     */
    public void setNumberOfKilometers(int numberOfKilometers) {
        this.numberOfKilometers = numberOfKilometers;
    }

    /**
     * Getter for guarantee
     * @return Boolean
     */
    public String getInGuarantee() {
        return this.inGuarantee;
    }

    /**
     * Setter for guarantee
     * @param inGuarantee String
     */
    public void setInGuarantee(String inGuarantee) {
        this.inGuarantee = inGuarantee;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", yearOfPurchase=" + yearOfPurchase +
                ", numberOfKilometers=" + numberOfKilometers +
                ", inGuarantee=" + inGuarantee +
                '}';
    }
}
