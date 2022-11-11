package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Util instance;

    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String connectionString = "jdbc:mysql://localhost:3306/mydb";
    private static final String login = "root";
    private static final String password = "LWQn2vCC";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, login, password);
            Class.forName(driverName);
            if (!connection.isClosed()) {
                System.out.println("Соединение установленно");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Соединение не установленно");
            e.printStackTrace();
        }
        return connection;
    }

    private Util() {
    }
    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }
}