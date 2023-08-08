package juno.repository;

import juno.model.BaseEntity;
import juno.model.TodoEntity;
import juno.repository.exception.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class JDBCRepository<TKey, TData extends BaseEntity<Integer>> implements IRepository<Integer, TData> {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/note_taker_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "juno10127";
    private HashMap<Integer, TData> entities;

    public JDBCRepository() {
        this.entities = new HashMap<Integer, TData>();
    }

    public int getSize() {
        return this.entities.size();
    }

    public boolean exists(Integer id) {
        return this.entities.containsKey(id);
    }
//    private Connection connect() {
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//            return connection;
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }

    private Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.out.println("Connected to Database.");
        return connection;
    }

    public TData getOne(Integer id) throws EntityNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TodoEntity newTodo = new TodoEntity(resultSet.getInt("id"), resultSet.getString("description"));
                this.entities.put(resultSet.getInt("id"), (TData) newTodo);

            }
            return this.entities.get(resultSet.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

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

