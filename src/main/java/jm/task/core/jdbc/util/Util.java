package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final MySqlConnector connector = MySqlConnector.getInstance("jdbc:mysql://127.0.0.1:3306/kata_1", "root", "");
    public static MySqlConnector getMySQLConnector () {
        return connector;
    }
}
