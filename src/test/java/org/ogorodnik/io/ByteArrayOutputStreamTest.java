package org.ogorodnik.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    public void testWrite() throws IOException {
        String content = "Hello world";
        byte[] b = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2);
        byteArrayOutputStream.write(b[0]);
        byteArrayOutputStream.write(b[1]);
        byteArrayOutputStream.write(b[2]);
    }
}