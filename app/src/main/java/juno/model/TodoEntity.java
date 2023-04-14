package juno.model;

import juno.repository.IRepository;

public class TodoEntity extends BaseEntity<Integer> {

    private String description;
    public TodoEntity(Integer id, String description) {
        super(id);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        String id = "ID: " + this.getId().toString();
        String description = "Description: " + this.getDescription().toString();
        return id.concat(" " + description);
    }
}
