package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ThrowingSupplier;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    ThrowingSupplier<Connection, SQLException> getMySQLConnection = Util::getMySQLConnection;

    public UserDaoJDBCImpl() {
    }

    public void execute(String query) {
        try (
                Connection connection = getMySQLConnection.get();
                Statement statement = connection.createStatement();
        ) {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void executeWithParameters(String query, String... args) {
        try (
                Connection connection = getMySQLConnection.get();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            for (int i = 0; i < args.length; i++) {
                statement.setString(i + 1, args[i]);
            }
            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createUsersTable() {
        String query = """
                CREATE TABLE `Users` (
                  `id` MEDIUMINT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NULL,
                  `lastName` VARCHAR(45) NULL,
                  `age` INT NULL,
                  PRIMARY KEY (`id`)
                );
                """;
        execute(query);
    }

    public void dropUsersTable() {
        String query = "DROP TABLE `Users`;";
        execute(query);
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO `Users` (`name`, `lastName`, `age`) VALUES (?, ?, ?);";
        executeWithParameters(query, name, lastName, String.valueOf(age));
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM `Users` WHERE id=?";
        executeWithParameters(query, String.valueOf(id));
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, lastName, age FROM `Users`";

        try (
                Connection connection = getMySQLConnection.get();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
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
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM `Users`";
        execute(query);
    }
}
