package net.mercury.hg.util;

import java.nio.charset.StandardCharsets;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

public class HashTool {

    /**
     * Calculate a 64 bits hash by combining CRC32 with Adler32.
     *
     * @param bytes a byte array
     * @return a hash number
     */
    public static long getHash64(byte[] bytes) {

        CRC32 crc32 = new CRC32();
        Adler32 adl32 = new Adler32();

        crc32.update(bytes);
        adl32.update(bytes);

        long crc = crc32.getValue();
        long adl = adl32.getValue();
        return ((crc << 32) | adl) + (crc << 8);
    }
    public static long getHash64(String s) {
        return getHash64(s.getBytes(StandardCharsets.UTF_8));
    }

}
