package br.upe.operations;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface HasherInterface {
    static String hash(String message) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            byte[] bytes = md.digest(message.getBytes());

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            // Return the hashed password
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
