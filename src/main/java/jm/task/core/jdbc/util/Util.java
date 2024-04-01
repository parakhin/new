package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hibernate.cfg.JdbcSettings.PASS;
import static org.hibernate.cfg.JdbcSettings.URL;
import static org.hibernate.cfg.JdbcSettings.USER;

public class Util {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/kata_1";
    private static final String user = "root";
    private static final String password = "";
    public static Connection getMySQLConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    static {
        System.out.println("123");
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty(URL, url)
                .setProperty(USER, user)
                .setProperty(PASS, password)
                .buildSessionFactory();

        // export the inferred database schema
        sessionFactory.getSchemaManager().exportMappedObjects(true);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User("Hibernate", "user", (byte) 6));
        transaction.commit();
        sessionFactory.close();

    }
}
