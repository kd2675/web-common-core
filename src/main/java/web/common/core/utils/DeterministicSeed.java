package web.common.core.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class DeterministicSeed {

    private DeterministicSeed() {
    }

    public static long fromUtf8(String value) {
        if (value == null) {
            return 0L;
        }
        byte[] digest;
        try {
            digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 is required for deterministic behavior seeds", ex);
        }
        long seed = 0L;
        for (int index = 0; index < 7; index++) {
            seed = (seed << 8) | (digest[index] & 0xffL);
        }
        return (seed << 4) | ((digest[7] >>> 4) & 0x0fL);
    }
}
