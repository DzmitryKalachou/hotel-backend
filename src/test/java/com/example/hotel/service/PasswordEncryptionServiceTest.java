package com.example.hotel.service;

import com.example.hotel.services.PasswordEncryptionService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncryptionServiceTest {

    private static final String PASSWORD = "PASSWORD";

    @Test
    public void testSaltGeneration() {
        final PasswordEncryptionService service = new PasswordEncryptionService();
        final byte[] salt = service.generateSalt();
        assertThat(salt).hasSize(8);
    }


    @Test
    public void testPasswordEncryption() {
        final PasswordEncryptionService service = new PasswordEncryptionService();
        final byte[] salt = service.generateSalt();

        final byte[] firstEncryptedPassword = service.getEncryptedPassword(PASSWORD, salt);
        final byte[] secondEncryptedPassword = service.getEncryptedPassword(PASSWORD, salt);
        assertThat(firstEncryptedPassword).isEqualTo(secondEncryptedPassword);
    }


    @Test
    public void testPasswordAuthentication() {
        final PasswordEncryptionService service = new PasswordEncryptionService();
        final byte[] salt = service.generateSalt();
        final byte[] encryptedPassword = service.getEncryptedPassword(PASSWORD, salt);
        assertThat(service.authenticate(PASSWORD, encryptedPassword, salt)).isTrue();
    }


}
