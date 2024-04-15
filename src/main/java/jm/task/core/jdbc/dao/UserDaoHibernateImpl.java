package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;
import java.util.function.Supplier;

public class UserDaoHibernateImpl implements UserDao {
    Supplier<SessionFactory> sessionFactorySupplier = Util::getSessionFactory;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (
                SessionFactory sessionFactory = Util.getSessionFactory();
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            NativeQuery nativeQuery = session.createNativeQuery("""
                      CREATE TABLE `Users` (
                      `id` MEDIUMINT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NULL,
                      `lastName` VARCHAR(45) NULL,
                      `age` INT NULL,
                      PRIMARY KEY (`id`)
                    )
                    """, User.class);


            nativeQuery.executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            System.out.println("Can`t create User table");
        }
    }

    @Override
    public void dropUsersTable() {
        try (
                SessionFactory sessionFactory = Util.getSessionFactory();
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            NativeQuery nativeQuery = session.createNativeQuery("""
                      DROP TABLE `Users`
                    """, User.class);

            nativeQuery.executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            System.out.println("Can`t drop users table");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (
                SessionFactory sessionFactory = sessionFactorySupplier.get();
                Session session = sessionFactory.openSession();
        ) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (
                SessionFactory sessionFactory = sessionFactorySupplier.get();
                Session session = sessionFactory.openSession();
        ) {
            session.remove(getUserById(id));
        }
    }

    public User getUserById(long id) {
        try (
                SessionFactory sessionFactory = sessionFactorySupplier.get();
                Session session = sessionFactory.openSession();
        ) {
            return session.load(User.class, id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (
                SessionFactory sessionFactory = sessionFactorySupplier.get();
                Session session = sessionFactory.openSession();
        ) {
            return session.createQuery("select a from User a", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (
                SessionFactory sessionFactory = Util.getSessionFactory();
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            NativeQuery nativeQuery = session.createNativeQuery("DELETE FROM `Users`", User.class);

            nativeQuery.executeUpdate();

            session.getTransaction().commit();
        }
    }
}
