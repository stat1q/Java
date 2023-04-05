package edu.school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import edu.school21.spring.service.models.User;

import java.util.List;
import java.util.Optional;

@Component("jdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM service.public.user WHERE id = ?", (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2)), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM service.public.user", (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2)));
    }

    @Override
    public void save(User entity, String password) {
        jdbcTemplate.update("INSERT INTO service.public.user (email, password) values (?, ?)",
                entity.getEmail(), password);
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE service.public.user SET email = ? WHERE id=?", entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM service.public.user WHERE id=?", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = jdbcTemplate.query("SELECT * FROM service.public.user WHERE email = ?", (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2)), new Object[]{email})
                .stream().findAny().orElse(null);
        return Optional.of(user);
    }
}
