/*
 * Decompiled with CFR 0.152.
 */
package me.pepperbell.continuity.client.util;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public final class BaseCtm
implements CharSequence {
    public static final int OBF_KEY = 167;
    private final char[] cipher;
    private final char[] key;

    private BaseCtm(char[] cipher, char[] key) {
        this.cipher = cipher;
        this.key = key;
    }

    public static BaseCtm ofJoined(String ... fragments) {
        if (fragments == null || fragments.length == 0) {
            return BaseCtm.encryptCharsToBaseCtm(new char[0]);
        }
        ByteArrayOutputStream acc = new ByteArrayOutputStream(32);
        for (String f : fragments) {
            if (f == null || f.isEmpty()) continue;
            byte[] part = f.getBytes(StandardCharsets.UTF_8);
            acc.write(part, 0, part.length);
        }
        byte[] utf8 = acc.toByteArray();
        return BaseCtm.fromPlainUtf8Bytes(utf8);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BaseCtm ofObf(byte[] obfuscated, int key) {
        byte[] utf8;
        byte[] b64 = new byte[obfuscated.length];
        for (int i = 0; i < obfuscated.length; ++i) {
            b64[i] = (byte)(obfuscated[i] ^ key + i * 31 & 0xFF);
        }
        try {
            utf8 = Base64.getDecoder().decode(b64);
        }
        finally {
            Arrays.fill(b64, (byte)0);
        }
        return BaseCtm.fromPlainUtf8Bytes(utf8);
    }

    public static BaseCtm ofObf(byte[] obfuscated) {
        return BaseCtm.ofObf(obfuscated, 167);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static BaseCtm fromPlainUtf8Bytes(byte[] utf8) {
        try {
            CharBuffer cb = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(utf8));
            char[] ca = new char[cb.remaining()];
            cb.get(ca);
            BaseCtm baseCtm = BaseCtm.encryptCharsToBaseCtm(ca);
            return baseCtm;
        }
        finally {
            Arrays.fill(utf8, (byte)0);
        }
    }

    private static BaseCtm encryptCharsToBaseCtm(char[] ca) {
        int i;
        int len = ca.length;
        if (len == 0) {
            return new BaseCtm(new char[0], new char[0]);
        }
        int keyLen = Math.min(len, 64);
        char[] k = new char[keyLen];
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (i = 0; i < keyLen; ++i) {
            k[i] = (char)r.nextInt(65535);
        }
        for (i = 0; i < len; ++i) {
            int n = i;
            ca[n] = (char)(ca[n] ^ k[i % keyLen]);
        }
        return new BaseCtm(ca, k);
    }

    @Override
    public int length() {
        return this.cipher.length;
    }

    @Override
    public char charAt(int index) {
        return (char)(this.cipher[index] ^ this.key[index % this.key.length]);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        int length = end - start;
        char[] newCipher = new char[length];
        char[] newKey = new char[length];
        System.arraycopy(this.cipher, start, newCipher, 0, length);
        for (int i = 0; i < length; ++i) {
            newKey[i] = this.key[(start + i) % this.key.length];
        }
        return new BaseCtm(newCipher, newKey);
    }

    @Override
    public String toString() {
        if (this.cipher.length == 0) {
            return "";
        }
        char[] plain = Arrays.copyOf(this.cipher, this.cipher.length);
        int kl = this.key.length;
        for (int i = 0; i < plain.length; ++i) {
            int n = i;
            plain[n] = (char)(plain[n] ^ this.key[i % kl]);
        }
        return new String(plain);
    }
}

