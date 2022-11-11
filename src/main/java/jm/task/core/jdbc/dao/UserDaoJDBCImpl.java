package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getInstance().getConnection();

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERSTABLE(" +
                "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100) NOT NULL, " +
                "LASTNAME VARCHAR(100) NOT NULL, AGE INT NOT NULL, PRIMARY KEY (ID))";

        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate(sql);
            System.out.println("Таблица создана");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERSTABLE";

        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate(sql);
            System.out.println("Таблица удалена");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERSTABLE (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";

        try (PreparedStatement preStat = connection.prepareStatement(sql)) {
            preStat.setString(1, name);
            preStat.setString(2, lastName);
            preStat.setLong(3, age);
            preStat.executeUpdate();
            System.out.println("Пользователь " + name + " " + lastName + " " + age + " Добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM USERSTABLE WHERE ID=?";

        try (PreparedStatement preStat = connection.prepareStatement(sql)) {
            preStat.setLong(1, id);
            preStat.executeUpdate();
            System.out.println("Пользователь удален");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERSTABLE";

        try (Statement stat = connection.createStatement()){
            ResultSet resultSet = stat.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(Byte.valueOf(resultSet.getString("AGE")));
                usersList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        PreparedStatement preparedStatement = null;
        String sql = "TRUNCATE TABLE USERSTABLE";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}