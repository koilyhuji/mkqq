package mkqq.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {
    public static String encodePassword(String password) {
        try {
            // Create an instance of the MessageDigest class with MD5 algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Update the digest with the password bytes
            md.update(password.getBytes());

            // Generate the hash (MD5 hash in byte array form)
            byte[] byteData = md.digest();

            // Convert the byte array into a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(String.format("%02x", b));  // Convert each byte to its hexadecimal representation
            }

            // Return the MD5 hash as a hexadecimal string
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;  // Return null in case of an error
        }
    }
}
