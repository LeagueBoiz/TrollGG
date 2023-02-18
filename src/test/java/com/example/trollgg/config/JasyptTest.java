package com.example.trollgg.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JasyptTest extends JasyptConfig {

    @Test
    public void jasypt_encrypt_decrypt_test() {
        String plainText = "안알려줌";

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("안알려줌2");

        String encryptedText = jasypt.encrypt(plainText);
        String decryptedText = jasypt.decrypt(encryptedText);

        System.out.println(encryptedText);
        System.out.println(decryptedText);

        assertThat(plainText).isEqualTo(decryptedText);
    }

}