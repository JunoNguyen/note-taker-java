package juno.model;

public class TodoEntity extends BaseEntity<Integer> {

    private String description;
    public TodoEntity(Integer id, String description) {
        super(id);
        this.description = description;
    }
}
