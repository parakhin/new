package jm.task.core.jdbc.util;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/kata_1";
    private static final String user = "root";
    private static final String password = "";
    public static Connection getMySQLConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    StandardServiceRegistryBuilder
}
