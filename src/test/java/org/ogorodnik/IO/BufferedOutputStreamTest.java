package org.ogorodnik.IO;

import org.junit.jupiter.api.Test;

//import java.io.BufferedOutputStream;
//import java.io.IOException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BufferedOutputStreamTest {

    @Test
    public void testWrite() throws IOException {
        String content = "Hello";
        byte[] buffer = content.getBytes();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(new ByteArrayOutputStream());
        bufferedOutputStream.write(buffer[0]);
        bufferedOutputStream.write(buffer[1]);
        bufferedOutputStream.write(buffer[2]);
    }

    @Test
    public void testWriteInputStreamArraySizeIsNotDefault() throws IOException {
        String content = "Hello";
        byte[] buffer = content.getBytes();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(new ByteArrayOutputStream(), 2);
        bufferedOutputStream.write(buffer[0]);
        bufferedOutputStream.write(buffer[1]);
        bufferedOutputStream.write(buffer[2]);

//       bufferedOutputStream.flush();
    }

    @Test
    public void testWriteIntoArray(){

    }

    @Test
    public void testWriteWithOffAndLen(){

    }

    @Test
    public void testFlush(){

    }

    @Test
    public void testClose(){

    }
}