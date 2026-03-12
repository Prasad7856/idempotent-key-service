package com.prasad.config;

import tools.jackson.databind.ObjectMapper;

import java.security.MessageDigest;
import java.util.Base64;

public class HashUtil {

    public static String hashRequest(Object obj) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(obj);

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(json.getBytes());

            return Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException("Hash generation failed");
        }
    }
}
