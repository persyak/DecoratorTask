package org.ogorodnik.IO;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ByteArrayInputStreamTest {
    @Test
    public void testReadBytesArray() {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals('H', (char) byteArrayInputStream.read());
        assertEquals('e', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('o', (char) byteArrayInputStream.read());
        assertEquals(-1, byteArrayInputStream.read());
    }

    @Test
    public void testReadToByteArray() throws IOException {
        String content = "Hello";
        byte[] buffer = new byte[content.getBytes().length + 1];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals(5, byteArrayInputStream.read(buffer));
        assertEquals('H', buffer[0]);
        assertEquals('e', buffer[1]);
        assertEquals('l', buffer[2]);
        assertEquals('l', buffer[3]);
        assertEquals('o', buffer[4]);
        assertEquals(0, buffer[5]);
        assertEquals(-1, byteArrayInputStream.read(buffer));
    }

    @Test
    public void testReadWithOffAndLen() throws IOException {
        String content = "Hello";
        byte[] buffer = new byte[10];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals(5, byteArrayInputStream.read(buffer, 1, 5));
        assertEquals(0, buffer[0]);
        assertEquals('H', buffer[1]);
        assertEquals('e', buffer[2]);
        assertEquals('l', buffer[3]);
        assertEquals('l', buffer[4]);
        assertEquals('o', buffer[5]);
        assertEquals(0, buffer[6]);
        assertEquals(-1, byteArrayInputStream.read(buffer, 1, 5));
    }

    @Test
    public void testClose() throws IOException {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        byteArrayInputStream.close();
    }
}