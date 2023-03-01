package com.example.autoserviceapp.repository;

/**
 * Abstract Repository class
 */
public abstract class AbstractRepository {
    protected String url, username, password;

    /**
     * Constructor with parameters
     * @param url String
     * @param username String
     * @param password String
     */
    public AbstractRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
