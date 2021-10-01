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
        String content = "Hello world";
        byte[] buffer = new byte[5];
        ByteArrayInputStream byteArrayInputStream
                = new ByteArrayInputStream(content.getBytes());
        assertEquals(4, byteArrayInputStream.read(buffer));
        assertEquals('H', (char) buffer[0]);
        assertEquals('e', (char) buffer[1]);
        assertEquals('l', (char) buffer[2]);
        assertEquals('l', (char) buffer[3]);
        assertEquals(0, buffer[4]);
    }

    @Test
    public void testReadWithOffAndLen() throws IOException {
        String content = "Hello world";
        byte[] buffer = new byte[5];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
//        int a = byteArrayInputStream.read(buffer, 0, 4);
//        int b = byteArrayInputStream.read(buffer, 0, 4);
//        int c = byteArrayInputStream.read(buffer, 0, 4);
//        int d = byteArrayInputStream.read(buffer, 0, 4);
        assertEquals(4, byteArrayInputStream.read(buffer, 0, 4));
        assertEquals('H', (char) buffer[0]);
        assertEquals('e', (char) buffer[1]);
        assertEquals('l', (char) buffer[2]);
        assertEquals('l', (char) buffer[3]);
        assertEquals(0, buffer[4]);
        assertEquals(4, byteArrayInputStream.read(buffer, 0, 4));
        assertEquals(3, byteArrayInputStream.read(buffer, 0, 4));
        assertEquals(-1, byteArrayInputStream.read(buffer, 0, 4));
    }

    @Test
    public void testClose() throws IOException {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        byteArrayInputStream.close();
        assertEquals('H', (char) byteArrayInputStream.read());;
    }
}