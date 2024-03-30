package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.MySqlConnector;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final MySqlConnector statement;

    public UserDaoJDBCImpl() {
        statement = Util.getMySQLConnector();
    }

    public void createUsersTable() {
        try {
            String query = """
                    CREATE TABLE `Users` (
                      `id` MEDIUMINT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NULL,
                      `lastName` VARCHAR(45) NULL,
                      `age` INT NULL,
                      PRIMARY KEY (`id`)
                    );
                    """;
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE `Users`;";
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = String.format("""
                    INSERT INTO `Users` (
                        `name`,
                        `lastName`,
                        `age`
                    ) VALUES (
                        "%s",
                        "%s",
                        %s
                    );
                """, name, lastName, age);

        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void removeUserById(long id) {
        String query = String.format("""
                    DELETE FROM `Users` WHERE id="%s";
                """, id);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, lastName, age FROM `Users`";
        try (ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        Byte.parseByte(resultSet.getString("age"))
                );
                user.setId(Long.parseLong(resultSet.getString("id")));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM `Users`";
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
