package com.example.hotel.services;

import com.auth0.jwt.JWT;
import com.example.hotel.db.tables.records.ApplicationUserRecord;
import com.example.hotel.errors.exceptions.InvalidPasswordException;
import com.example.hotel.errors.exceptions.UserNotExistsException;
import com.example.hotel.model.requests.LoginRequest;
import com.example.hotel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
@Transactional
@Slf4j
public class UserService {

    private static final long EXPIRATION_TIME = 1000 * 60 * 24 * 10; //10 days
    private final UserRepository userRepository;
    private final PasswordEncryptionService passwordEncryptionService;
    @Value("${jwt.secret}")
    private final String jwtSecret;


    public UserService(final UserRepository userRepository, final PasswordEncryptionService passwordEncryptionService,
        @Value("${jwt.secret}") final String jwtSecret) {
        this.userRepository = userRepository;
        this.passwordEncryptionService = passwordEncryptionService;
        this.jwtSecret = jwtSecret;
    }

    public String login(final LoginRequest request) {
        final ApplicationUserRecord userByEmail = userRepository.getUserByEmail(request.getEmail());
        if (userByEmail == null) {
            throw new UserNotExistsException();
        }
        if (!passwordEncryptionService.authenticate(request.getPassword(), userByEmail.getPassword(), userByEmail.getSalt())) {
            throw new InvalidPasswordException();
        }
        return buildAuthToken(userByEmail.getId());
    }

    private String buildAuthToken(final Long userId) {
        return JWT.create()
            .withSubject(userId.toString())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(HMAC512(jwtSecret.getBytes()));
    }


    public boolean isUserExists(final Long userId) {
        return userRepository.isUserExistsById(userId);
    }
}
