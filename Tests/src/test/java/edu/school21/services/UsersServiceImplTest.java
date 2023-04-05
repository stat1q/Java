package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
//import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UsersServiceImplTest {

    private final UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
    private final UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);

    @Test
    void authorizeForCorrectUser() throws SQLException {
        User user = new User(1L, "user", "password");
        when(usersRepository.findByLogin(anyString())).thenReturn(user);
        boolean result = usersService.authenticate(user.getLogin(), user.getPassword());
        Assertions.assertTrue(result);
    }

    @Test
    void authorizeForIncorrectUser() throws SQLException {
        User user = new User(1L, "user", "password");
        when(usersRepository.findByLogin(anyString())).thenThrow(EntityNotFoundException.class);
        boolean result = usersService.authenticate(user.getLogin(), user.getPassword());
        Assertions.assertFalse(result);
    }

    @Test
    void authorizeWithIncorrectPassword() throws SQLException {
        User user = new User(1L, "user", "password");
        when(usersRepository.findByLogin(anyString())).thenReturn(user);
        boolean result = usersService.authenticate(user.getLogin(), "new_password");
        Assertions.assertFalse(result);
    }


    @Test
    void authorizeForUserAlreadyAuthenticated() throws SQLException {
        User user = new User(1L, "user", "password");
        user.setAuthenticationSuccessStatus(true);
        when(usersRepository.findByLogin(anyString())).thenReturn(user);
        Assertions.assertThrows(
                AlreadyAuthenticatedException.class,
                () -> usersService.authenticate(user.getLogin(), user.getPassword()));
    }
}
