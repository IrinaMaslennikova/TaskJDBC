package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `users` (" +
                "`id` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(45) NULL," +
                "`lastName` VARCHAR(45) NULL," +
                "`age` TINYINT(1) NULL," +
                "PRIMARY KEY (`id`));";
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS `users`;";
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO `users` (name, lastName, age) VALUE (?, ?, ?);";
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM `users` WHERE id = ?;";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM `users`;";
        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE `users`;";
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
