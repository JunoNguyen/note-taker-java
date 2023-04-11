package juno.repository;

import juno.repository.exception.EntityNotFoundException;

import java.util.ArrayList;

/**
 * IRepository defines a method to get, store, and retrieve entities
 */

public interface IRepository<TKey, TData> {
    /**
     * Get an entity `T` by its identifier
     */
    public TData getOne(TKey id) throws EntityNotFoundException;

   ArrayList<TData> getAll();

    /**
     * Delete an entity by its identifier
     */
    public void deleteOne(TKey id);

    /**
     * Add an entity
     */
    public TData addOne(TData entity);

    public TData updateOne(TKey id, TData entity);

    /**
     * Get the total number of entities in this repository
     */
    public int getSize();

    /**
     * Check if an entity exists by a key
     */
    public boolean exists(TKey id);
}