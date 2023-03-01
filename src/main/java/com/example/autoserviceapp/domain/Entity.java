package com.example.autoserviceapp.domain;

/**
 * Entity abstract class
 */
public abstract class Entity {
    int id;

    /**
     * Default constructor
     */
    public Entity() {
        this.id = 0;
    }

    /**
     * Getter for id
     * @return Integer
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id Integer
     */
    public void setId(int id) {
        this.id = id;
    }
}
