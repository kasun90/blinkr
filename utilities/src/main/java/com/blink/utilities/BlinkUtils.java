package com.blink.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class BlinkUtils {

    public static String sha256(String input) throws Exception {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        byte[] digest = instance.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            String hex = Integer.toHexString(0xff & digest[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
