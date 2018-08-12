package com.example.hotel.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;


@Service
@Slf4j
public class PasswordEncryptionService {

    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) {
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    public byte[] getEncryptedPassword(String password, byte[] salt) {
        try {
            final String algorithm = "PBKDF2WithHmacSHA1";
            final int derivedKeyLength = 160;
            final int iterations = 20000;
            final KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
            final SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
            return f.generateSecret(spec)
                .getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOG.error("infrastructure problem", e);
            throw new IllegalStateException(e.getMessage());
        }
    }

    public byte[] generateSalt() {
        try {
            final SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            final byte[] salt = new byte[8];
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            LOG.error("infrastructure problem", e);
            throw new IllegalStateException(e.getMessage());
        }
    }
}
