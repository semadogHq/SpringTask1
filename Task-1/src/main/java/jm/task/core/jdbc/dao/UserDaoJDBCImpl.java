package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getInstance().getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {  // ddl operation -> statement ( executeUpdate )
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "last_name VARCHAR(255), " +
                    "age INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) { //добавление юзеров в табл
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, " +
                "last_name, " +
                "age)" +
                "VALUES (?, ?, ?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {                   //получение юзеров из таблицы

        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery(
                "SELECT *" +
                " FROM users")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void removeUserById(long id) { //deleting users from table (by id)
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM users" +
                " WHERE id = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleanUsersTable() {//очистка таблицы

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public void dropUsersTable() { //удаление таблицы юзеров не должно приводить к искл, если таб нет
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}