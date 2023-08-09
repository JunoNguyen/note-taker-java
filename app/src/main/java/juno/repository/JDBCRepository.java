package juno.repository;

import juno.model.BaseEntity;
import juno.model.TodoEntity;
import juno.repository.exception.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    private Connection connect() throws SQLException {
        this.entities = new HashMap<Integer, TData>();
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

            preparedStatement.close();
            connection.close();
            return this.entities.get(resultSet.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<TData> getAll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM todo";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                TodoEntity newTodo = new TodoEntity(resultSet.getInt("id"), resultSet.getString("description"));
                this.entities.put(resultSet.getInt("id"), (TData) newTodo);

            }

            statement.close();
            connection.close();
            return new ArrayList<>(this.entities.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOne(Integer id) throws EntityNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todo WHERE id = ?");
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected < 0) {
                throw new EntityNotFoundException("No entity found");
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TData addOne(TData entity) {
        try {
            String[] stringEntity = String.valueOf(entity).split(": ");
            String[] newStringEntity = new String[stringEntity.length - 2];
            for (int i = 2; i < stringEntity.length; i++) {
                newStringEntity[i - 2] = stringEntity[i];
            };

            String description = Arrays.toString(newStringEntity).replace("[", "").replace("]", "").replace(",", ":").trim();

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO todo (description) VALUES (?)");
            preparedStatement.setString(1, description);
            int rowsAffected = preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.close();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

        public TData updateOne(Integer id, TData entity) throws EntityNotFoundException {
            try {
                String[] stringEntity = String.valueOf(entity).split(": ");
                String[] newStringEntity = new String[stringEntity.length - 2];
                for (int i = 2; i < stringEntity.length; i++) {
                    newStringEntity[i - 2] = stringEntity[i];
                };

                String description = Arrays.toString(newStringEntity).replace("[", "").replace("]", "").replace(",", ":").trim();

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = connect();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE todo SET description = ? WHERE id = ?");
                preparedStatement.setString(1, description);
                preparedStatement.setInt(2, id);
                int rowsAffected = preparedStatement.executeUpdate();


                preparedStatement.close();
                connection.close();
                return entity;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }
}

