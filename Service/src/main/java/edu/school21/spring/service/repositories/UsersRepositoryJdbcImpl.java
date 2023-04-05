package edu.school21.spring.service.repositories;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("jdbcStandard")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    DataSource dataSource;

    @Autowired
    public UsersRepositoryJdbcImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String SQL_QUERY = "SELECT * FROM service.public.user WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            resultSet.next();
            user = new User(resultSet.getLong(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String SQL_QUERY = "SELECT * FROM service.public.user";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next())
                userList.add(new User(resultSet.getLong(1), resultSet.getString(2)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void save(User entity, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO service.public.user VALUES (?, ?);");) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        String SQL_QUERY = "UPDATE service.public.user " + "SET email = ? " + "WHERE id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setLong(2, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM service.public.user " + "WHERE id = ?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM service.public.user " + "WHERE email = ?;")) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getLong(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(user);
    }
}
