package com.example.hotel.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.hotel.db.tables.records.ApplicationUserRecord;
import com.example.hotel.errors.exceptions.InvalidPasswordException;
import com.example.hotel.errors.exceptions.UserNotExistsException;
import com.example.hotel.model.requests.LoginRequest;
import com.example.hotel.repositories.UserRepository;
import com.example.hotel.services.PasswordEncryptionService;
import com.example.hotel.services.UserService;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private static final String EMAIL = "test_email";
    private static final Long ID = 1L;
    private static final String PASSWORD = "test_password";
    private static final byte[] SALT = new byte[]{0, 1, 2, 3, 4, 5, 6, 7};
    private static final byte[] ENCRYPTED_PASSWORD = new byte[]{0, 1, 2, 3, 4, 5, 6, 7};
    private static final String SECRET = "secret";

    private final PasswordEncryptionService passwordEncryptionService = mock(PasswordEncryptionService.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserService userService = new UserService(userRepository, passwordEncryptionService, SECRET);

    @Test(expected = UserNotExistsException.class)
    public void userNotExistsOnLoginTest() {
        when(userRepository.getUserByEmail(EMAIL)).thenReturn(null);
        userService.login(new LoginRequest().setEmail(EMAIL)
            .setPassword(PASSWORD));
    }

    @Test(expected = InvalidPasswordException.class)
    public void invalidPasswordOnLoginTest() {
        final ApplicationUserRecord userRecord = mock(ApplicationUserRecord.class);
        when(userRecord.getPassword()).thenReturn(ENCRYPTED_PASSWORD);
        when(userRecord.getSalt()).thenReturn(SALT);
        when(userRepository.getUserByEmail(EMAIL)).thenReturn(userRecord);
        when(passwordEncryptionService.authenticate(PASSWORD, ENCRYPTED_PASSWORD, SALT)).thenReturn(false);

        userService.login(new LoginRequest().setEmail(EMAIL)
            .setPassword(PASSWORD));
    }

    @Test
    public void loginTest() {
        final ApplicationUserRecord userRecord = mock(ApplicationUserRecord.class);
        when(userRecord.getPassword()).thenReturn(ENCRYPTED_PASSWORD);
        when(userRecord.getSalt()).thenReturn(SALT);
        when(userRepository.getUserByEmail(EMAIL)).thenReturn(userRecord);
        when(passwordEncryptionService.authenticate(PASSWORD, ENCRYPTED_PASSWORD, SALT)).thenReturn(true);

        final String token = userService.login(new LoginRequest().setEmail(EMAIL)
            .setPassword(PASSWORD));
        assertThat(token).isNotBlank();
        assertThat(token).matches(e -> {
            JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(e);
            return true;
        });

    }

    @Test
    public void userExistsTest() {
        when(userRepository.isUserExistsById(ID)).thenReturn(true);
        assertThat(userService.isUserExists(ID)).isTrue();
        verify(userRepository, only()).isUserExistsById(ID);
        verifyNoMoreInteractions(userRepository);
    }


}
