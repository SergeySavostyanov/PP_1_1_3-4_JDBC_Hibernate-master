package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    // создание таблицы
    public void createUsersTable() {
        Connection connection = Util.getConnection();

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydbstest`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `last_name` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы users");
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }


    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы users  из БД");
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement("INSERT INTO users(name,last_name,age) VALUES (?,?,?)")) {
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя в таблицу");
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM users WHERE id=" + id);
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователь с id=" + id);
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = Util.getConnection();
        try (ResultSet set = connection.createStatement().executeQuery("SELECT * FROM users")) {
            connection.setAutoCommit(false);
            while (set.next()) {
                User user = new User(set.getString("name"),
                        set.getString("last_name"), set.getByte("age"));
                user.setId(set.getLong("id"));
                users.add(user);
                connection.commit();

            }
        } catch (SQLException e) {
            System.out.println("Ошибка при создании списка");
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {

            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE users");
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }
}
