package juno.model;

public class BaseEntity<TKey> {
    private TKey id;

    public BaseEntity(TKey id) {
        this.id = id;
    }

    public TKey getId() {
        return id;
    }
}
