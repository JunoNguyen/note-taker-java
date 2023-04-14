package juno.repository;

import juno.model.BaseEntity;
import juno.repository.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An in-memory implementation of a repository
 *
 * @param <TData> -- Class of entities stored in this repository
 */
public class InMemoryRepository<TKey, TData extends BaseEntity<Integer>> implements IRepository<Integer, TData> {
    private HashMap<Integer, TData> entities;

    public InMemoryRepository() {
        this.entities = new HashMap<Integer, TData>();
    }

    public int getSize() {
        return this.entities.size();
    }

    public boolean exists(Integer id) {
        return this.entities.containsKey(id);
    }

    @Override
    public TData getOne(Integer id) throws EntityNotFoundException {
        if (this.entities.containsKey(id)) {
            return this.entities.get(id);
        } else {
            throw new EntityNotFoundException("No entity found.");
        }
    }

    @Override
    public ArrayList<TData> getAll() {
        return new ArrayList<>(this.entities.values());
    }

    @Override
    public void deleteOne(Integer id) throws EntityNotFoundException {
        if (this.entities.containsKey(id)) {
            this.entities.remove(id);
        } else {
            throw new EntityNotFoundException("No entity found");
        }
    }

    @Override
    public TData addOne(TData entity) {
        this.entities.put(entity.getId(), entity);
        return entity;
    }

    public TData updateOne(Integer id, TData entity) throws EntityNotFoundException {
        if (this.entities.containsKey(id)) {
            this.entities.replace(id, entity);
            return entity;
        } else {
            throw new EntityNotFoundException("No entity found");
        }
    }
}