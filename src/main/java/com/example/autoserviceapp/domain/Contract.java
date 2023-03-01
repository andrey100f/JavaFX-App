package com.example.autoserviceapp.domain;

/**
 * Contract class
 */
public class Contract extends Entity {
    private int idCar, idClientCard, sumOfParts, sumOfWork;

    /**
     * Default constructor
     */
    public Contract() {
        this.idCar = 0;
        this.idClientCard = 0;
        this.sumOfParts = 0;
        this.sumOfWork = 0;
    }

    /**
     * Constructor with parameters
     * @param idCar Integer
     * @param idClientCard Integer
     * @param sumOfParts Integer
     * @param sumOfWork Integer
     */
    public Contract(int idCar, int idClientCard, int sumOfParts, int sumOfWork) {
        this.idCar = idCar;
        this.idClientCard = idClientCard;
        this.sumOfParts = sumOfParts;
        this.sumOfWork = sumOfWork;
    }

    /**
     * Getter for car id
     * @return Integer
     */
    public int getIdCar() {
        return idCar;
    }

    /**
     * Setter for id car
     * @param idCar Integer
     */
    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    /**
     * Getter for client card id
     * @return Integer
     */
    public int getIdClientCard() {
        return idClientCard;
    }

    /**
     * Setter for client card id
     * @param idClientCard Integer
     */
    public void setIdClientCard(int idClientCard) {
        this.idClientCard = idClientCard;
    }

    /**
     * Getter for sum of parts
     * @return Integer
     */
    public int getSumOfParts() {
        return sumOfParts;
    }

    /**
     * Setter for sum of parts
     * @param sumOfParts Integer
     */
    public void setSumOfParts(int sumOfParts) {
        this.sumOfParts = sumOfParts;
    }

    /**
     * Getter for sum of work
     * @return Integer
     */
    public int getSumOfWork() {
        return sumOfWork;
    }

    /**
     * Setter for sum of work
     * @param sumOfWork Integer
     */
    public void setSumOfWork(int sumOfWork) {
        this.sumOfWork = sumOfWork;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "idCar=" + idCar +
                ", idClientCard=" + idClientCard +
                ", sumOfParts=" + sumOfParts +
                ", sumOfWork=" + sumOfWork +
                '}';
    }
}
