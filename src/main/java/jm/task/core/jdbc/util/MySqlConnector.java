package jm.task.core.jdbc.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MySqlConnector implements Closeable {
    private final String url;
    private final String user;
    private final String password;
    private Statement statement;
    private Connection connection;
    public static final Map<Integer, MySqlConnector> instances = new HashMap<>();

    private MySqlConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static MySqlConnector getInstance(String url, String user, String password) {
        int hashcode = Objects.hash(url, user);
        return instances.computeIfAbsent(hashcode, (i) -> new MySqlConnector(url, user, password));
    }

    public void execute(String query) throws SQLException {
        getStatement().execute(query);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return getStatement().executeQuery(query);
    }

    private Statement getStatement() {
        createStatement();
        return statement;
    }

    private void createStatement() {
        if (statement != null) {
            return;
        }
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
