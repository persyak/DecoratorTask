package org.ogorodnik.IO;

import org.junit.jupiter.api.Test;

import java.io.*;
//import java.io.BufferedInputStream;
//import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class BufferedInputStreamTest {

    @Test
    public void testReadIntoArray() throws IOException {
        String content = "Hello world its a beautiful day tomorrow";
        ByteArrayInputStream x = new ByteArrayInputStream(content.getBytes());
        BufferedInputStream y = new BufferedInputStream(x, 5);
        java.io.BufferedInputStream bufferedInputStream
                = new java.io.BufferedInputStream(x, 10);
        byte[] buffer = new byte[6];
        bufferedInputStream.read(buffer, 1, 3);
        bufferedInputStream.read(buffer, 1, 4);
        bufferedInputStream.read(buffer, 1, 4);
        bufferedInputStream.read(buffer, 1, 4);
//        bufferedInputStream.close();
//        assertEquals('H', (char) buffer[0]);
//        assertEquals('e', (char) buffer[1]);
//        assertEquals('l', (char) buffer[2]);
//        assertEquals('l', (char) buffer[3]);
//        assertEquals('o', (char) buffer[4]);
//        assertEquals(0, buffer[5]);
    }

    @Test
    public void testReadIntoArrayWithCount() throws IOException {
        String content = "Hello world";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()));
        byte[] buffer = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(buffer, 1, 5);
        assertEquals(0, buffer[0]);
        assertEquals('H', (char) buffer[1]);
        assertEquals('e', (char) buffer[2]);
        assertEquals('l', (char) buffer[3]);
        assertEquals('l', (char) buffer[4]);
        assertEquals('o', (char) buffer[5]);
        assertEquals(0, buffer[6]);
        assertEquals(0, buffer[bufferedInputStream.available()]);
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
    public void readWithoutArguments() throws IOException {
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
}