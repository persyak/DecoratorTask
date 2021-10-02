package org.ogorodnik.IO;

import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class BufferedInputStreamTest {

    @Test
    public void testReadIntoArray() throws IOException {
        String content = "Hello world";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream((new ByteArrayInputStream(content.getBytes())));
        byte[] buffer = new byte[6];
        assertEquals(6, bufferedInputStream.read(buffer));
        assertEquals(5, bufferedInputStream.read(buffer));
        assertEquals(-1, bufferedInputStream.read(buffer));
    }

    @Test
    public void testReadIntoArrayWhenInputStreamArraySizeIsNotDefault() throws IOException {
        String content = "Hello world";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream((new ByteArrayInputStream(content.getBytes())), 10);
        byte[] buffer = new byte[6];
        assertEquals(6, bufferedInputStream.read(buffer));
        assertEquals(4, bufferedInputStream.read(buffer));
        assertEquals(1, bufferedInputStream.read(buffer));
        assertEquals(-1, bufferedInputStream.read(buffer));
    }

    @Test
    public void testReadWithOffAndLen() throws IOException {
        String content = "Hello world";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream((new ByteArrayInputStream(content.getBytes())), 10);
        byte[] buffer = new byte[6];
        assertEquals(3, bufferedInputStream.read(buffer, 1, 3));
        assertEquals(4, bufferedInputStream.read(buffer, 1, 4));
        assertEquals(3, bufferedInputStream.read(buffer, 1, 4));
        assertEquals(1, bufferedInputStream.read(buffer, 1, 4));
        assertEquals(-1, bufferedInputStream.read(buffer, 1, 4));
    }

    @Test
    public void testClose() throws IOException {
        String content = "Hello world";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()));
        assertEquals('H', (char)bufferedInputStream.read());
        bufferedInputStream.close();
        assertThrows(IOException.class, bufferedInputStream::read);
    }

    @Test
    public void testRead() throws IOException {
        String content = "Hello";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()));
        assertEquals('H', (char)bufferedInputStream.read());
        assertEquals('e', (char)bufferedInputStream.read());
        assertEquals('l', (char)bufferedInputStream.read());
        assertEquals('l', (char)bufferedInputStream.read());
        assertEquals('o', (char)bufferedInputStream.read());
        assertEquals(-1, bufferedInputStream.read());
    }

    @Test
    public void testReadWhenInputStreamArraySizeIsNotDefault() throws IOException {
        String content = "Hello";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()), 4);
        assertEquals('H', (char)bufferedInputStream.read());
        assertEquals('e', (char)bufferedInputStream.read());
        assertEquals('l', (char)bufferedInputStream.read());
        assertEquals('l', (char)bufferedInputStream.read());
        assertEquals('o', (char)bufferedInputStream.read());
        assertEquals(-1, bufferedInputStream.read());
    }
}