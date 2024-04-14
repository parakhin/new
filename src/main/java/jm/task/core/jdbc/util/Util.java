package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.schema.Action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.JdbcSettings.PASS;
import static org.hibernate.cfg.JdbcSettings.URL;
import static org.hibernate.cfg.JdbcSettings.USER;

public class Util {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/kata_1";
    private static final String user = "root";
    private static final String password = "";

//    private static final Configuration hibernateConfiguration = new Configuration()
//            .addAnnotatedClass(User.class)
//            .setProperty(URL, url)
//            .setProperty(USER, user)
//            .setProperty(PASS, password);

    public static Connection getMySQLConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    public static SessionFactory getSessionFactory() {

        return new Configuration()
                        .addAnnotatedClass(User.class)
                        .setProperty(AvailableSettings.JAKARTA_JDBC_URL, url)
                        .setProperty(AvailableSettings.JAKARTA_JDBC_USER, user)
                        .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, password)

//                        .setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION,
//                                Action.SPEC_ACTION_DROP_AND_CREATE)

                        .setProperty(AvailableSettings.SHOW_SQL, TRUE.toString())
                        .setProperty(AvailableSettings.FORMAT_SQL, TRUE.toString())
                        .setProperty(AvailableSettings.HIGHLIGHT_SQL, TRUE.toString())
                        .buildSessionFactory();
    }
}
