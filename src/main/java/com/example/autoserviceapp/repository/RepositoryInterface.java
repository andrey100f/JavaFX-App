package com.example.autoserviceapp.repository;

import java.util.List;

/**
 * RepositoryInterface class
 * @param <E> Entity object
 */
public interface RepositoryInterface<E> {
    /**
     * Adds an entity in repo
     * @param entity Entity object
     */
    void addEntity(E entity);

    /**
     * Updates an entity in repo
     * @param id Integer
     * @param newEntity Entity object
     */
    void updateEntity(int id, E newEntity);

    /**
     * Removes an entity from the repository
     * @param id Integer
     */
    void removeEntity(int id);

    /**
     * Gets an entity by id
     * @param id Integer
     * @return Entity object
     */
    E getEntityById(int id);

    /**
     * Gets all the entities from the repo
     * @return List object
     */
    List<E> getAllEntities();
}
