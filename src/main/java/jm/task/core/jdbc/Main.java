package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        Connection connection = Util.getInstance().getConnection();

        connection.setAutoCommit(false);
        userService.createUsersTable();
        userService.saveUser("Andrey", "Ivanov", (byte) 25);
        userService.saveUser("Ivan", "Sidorov", (byte) 61);
        userService.saveUser("Masha", "Petrova", (byte) 47);
        userService.saveUser("Vika", "Matveev", (byte) 30);
        connection.commit();
        userService.removeUserById(1);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        connection.setAutoCommit(true);
    }
}