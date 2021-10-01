package org.ogorodnik.IO;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ByteArrayOutputStreamTest {

    @Test
    public void testWriteIntoByteArray() throws IOException {
        String content = "Hello world";
        byte[] b = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(b);
    }

    @Test
    public void testWriteWithOffAndLen() throws IOException {
        String content = "Hello world";
        byte[] b = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(b, 0, 4);
        byteArrayOutputStream.flush();
        byteArrayOutputStream.write(b, 6, 5);
    }

    @Test
    public void testFlush() throws IOException {
        //method flush does nothing so no need to test it
    }

    @Test
    public void testClose() throws IOException {
        //method close does nothing so no need to test it
    }

    @Test
    public void testWrite() throws IOException {
        String content = "Hello world";
        byte[] b = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2);
        byteArrayOutputStream.write(b[0]);
        byteArrayOutputStream.write(b[1]);
        byteArrayOutputStream.write(b[2]);
//        byte[] x = byteArrayOutputStream.toByteArray();

    }
}