package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        new UserServiceImpl().createUsersTable();
        new UserServiceImpl().saveUser("User1", "Surname1", (byte) 20);
        new UserServiceImpl().saveUser("User2", "Surname2", (byte) 25);
        new UserServiceImpl().saveUser("User3", "Surname3", (byte) 30);
        new UserServiceImpl().saveUser("User4", "Surname4", (byte) 35);
        new UserServiceImpl().getAllUsers().forEach(System.out::println);
        new UserServiceImpl().cleanUsersTable();
        new UserServiceImpl().dropUsersTable();
    }
}
