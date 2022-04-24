package com.vik.snl.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {
    private static final Random random = new Random();

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    // Both inclusive
    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
