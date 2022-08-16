package com.boutiqueseeds.boutiqueSeeds.services;

import java.util.UUID;

public class GenerateLocator {
    public static String generateLocatorString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0,8) ;
    }
}
