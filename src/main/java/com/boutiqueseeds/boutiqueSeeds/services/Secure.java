package com.boutiqueseeds.boutiqueSeeds.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Secure {
    public static String Hash(String s) throws NoSuchAlgorithmException {
        String generatedPassword = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(s.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPassword = sb.toString();
        return generatedPassword;
    }
}
