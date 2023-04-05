package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws SQLException {
        User user;
        try {
            user = usersRepository.findByLogin(login);
        } catch (EntityNotFoundException e) {
            return false;
        }
        if (user.getAuthenticationSuccessStatus()) {
            throw new AlreadyAuthenticatedException("User: " + login + " already authenticated!");
        }
        if (user.getPassword().equals(password)) {
            user.setAuthenticationSuccessStatus(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}
