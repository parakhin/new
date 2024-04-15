package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import static java.lang.Boolean.TRUE;


public class Util {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/kata_1";
    private static final String user = "root";
    private static final String password = "";

    public static SessionFactory getSessionFactory() {

        return new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, url)
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, user)
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, password)
                .setProperty(AvailableSettings.SHOW_SQL, TRUE.toString())
                .setProperty(AvailableSettings.FORMAT_SQL, TRUE.toString())
                .setProperty(AvailableSettings.HIGHLIGHT_SQL, TRUE.toString())
                .buildSessionFactory();
    }
}
