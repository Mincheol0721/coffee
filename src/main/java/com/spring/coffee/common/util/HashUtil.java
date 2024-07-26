package com.spring.coffee.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HashUtil {
	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		log.info("*".repeat(90));
		log.info("넘겨받은 password: {}", password);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        log.info("해시화 된 password: {}", hexString.toString().toUpperCase());
        log.info("*".repeat(90));
        return hexString.toString().toUpperCase(); // SQL Server는 대문자를 사용합니다.
    }
}
