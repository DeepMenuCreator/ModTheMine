package net.novabruteforce;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
    private static final String CHARS = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM.@#$_*\"'&-+()/:;!~£`¢|€•¥√^π°÷=×{§}∆%©®™✓[]";
    private final Random random = new SecureRandom();
    private int length = 8;

    public String generate() {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        length = (length % 16) + 4;
        return sb.toString();
    }
}
