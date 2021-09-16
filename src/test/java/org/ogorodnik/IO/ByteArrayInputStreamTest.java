package org.ogorodnik.IO;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ByteArrayInputStreamTest {
    @Test
    public void test(){
        String content = "Hello";
        java.io.ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals('H', (char) byteArrayInputStream.read());
        assertEquals('e', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('o', (char) byteArrayInputStream.read());
        assertEquals(-1, byteArrayInputStream.read());
    }
}