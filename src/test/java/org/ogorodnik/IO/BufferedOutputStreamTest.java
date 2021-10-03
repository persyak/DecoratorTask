package org.ogorodnik.IO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BufferedOutputStreamTest {

    @Test
    public void testWrite() throws IOException {
        String content = "Hello";
        byte[] buffer = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(byteArrayOutputStream, 4);
        bufferedOutputStream.write(buffer[0]);
        assertEquals(1, bufferedOutputStream.bufIndex);
        assertEquals('H', bufferedOutputStream.buf[0]);
        bufferedOutputStream.write(buffer[1]);
        bufferedOutputStream.write(buffer[2]);
        bufferedOutputStream.write(buffer[3]);
        assertEquals(4, bufferedOutputStream.bufIndex);
        assertEquals('l', bufferedOutputStream.buf[3]);
        bufferedOutputStream.write(buffer[4]);
        assertEquals(1, bufferedOutputStream.bufIndex);
        assertEquals(4, byteArrayOutputStream.getPosition());
        byte[] byteArrayOutputStreamArray = byteArrayOutputStream.getBytes();
        assertEquals('H', byteArrayOutputStreamArray[0]);
    }

    @Test
    public void testWriteFromArrayWhenBufIsBiggerThanArray() throws IOException {
        String content = "Hello world its beautiful";
        byte[] buffer = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(byteArrayOutputStream);
        bufferedOutputStream.write(buffer);
        assertEquals(25, bufferedOutputStream.bufIndex);
        assertEquals(0, byteArrayOutputStream.getPosition());
        assertEquals('H', bufferedOutputStream.buf[0]);
    }

    @Test
    public void testWriteFromArrayWhenBufIsSmallerThanArray() throws IOException {
        String content = "Hello world its beautiful";
        byte[] buffer = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(byteArrayOutputStream, 24);
        bufferedOutputStream.write(buffer);
        assertEquals(0, bufferedOutputStream.bufIndex);
        assertEquals(25, byteArrayOutputStream.getPosition());
        byte[] byteArrayOutputStreamArray = byteArrayOutputStream.getBytes();
        assertEquals('H', byteArrayOutputStreamArray[0]);
    }

    @Test
    public void testWriteWithOffAndLenWithStandardBufSizeWhenLenIsSmallerThenBufSize() throws IOException {
        String content = "Hello world its beautiful season for barbeque";
        byte[] buffer = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(byteArrayOutputStream);
        bufferedOutputStream.write(buffer, 1, 8);
        assertEquals(8, bufferedOutputStream.bufIndex);
        assertEquals(0, byteArrayOutputStream.getPosition());
        assertEquals('e', (char)bufferedOutputStream.buf[0]);
        assertEquals('r', (char)bufferedOutputStream.buf[7]);
        byte[] byteArrayOutputStreamArray = byteArrayOutputStream.getBytes();
        assertEquals(0, byteArrayOutputStreamArray[0]);
        assertEquals(0, byteArrayOutputStreamArray[7]);
    }

    @Test
    public void testWriteWithOffAndLenWithStandardBufSizeWhenLenIsBiggerThenBufSize() throws IOException {
        String content = "Hello world its beautiful season for barbeque";
        byte[] buffer = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(byteArrayOutputStream);
        bufferedOutputStream.write(buffer, 1, 35);
        assertEquals(0, bufferedOutputStream.bufIndex);
        assertEquals(35, byteArrayOutputStream.getPosition());
        assertEquals(0, bufferedOutputStream.buf[0]);
        byte[] byteArrayOutputStreamArray = byteArrayOutputStream.getBytes();
        assertEquals('e', (char) byteArrayOutputStreamArray[0]);
        assertEquals('r', (char) byteArrayOutputStreamArray[34]);
    }

    @Test
    public void testWriteWithOffAndLenWithCustomBufSizeWhenLenIsSmallerThenBufSizeWhenCallingWriteFewTimes() throws IOException {
        String content = "Hello world its beautiful";
        byte[] buffer = content.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(byteArrayOutputStream, 7);
        bufferedOutputStream.write(buffer, 1, 5);
        assertEquals('e', (char) bufferedOutputStream.buf[0]);
        assertEquals(5, bufferedOutputStream.bufIndex);
        assertEquals(0, byteArrayOutputStream.getPosition());
        bufferedOutputStream.write(buffer, 6, 5);
        assertEquals(5, bufferedOutputStream.bufIndex);
        assertEquals(5, byteArrayOutputStream.getPosition());
        assertEquals('w', (char) bufferedOutputStream.buf[0]);
        byte[] byteArrayOutputStreamArray = byteArrayOutputStream.getBytes();
        assertEquals('e', (char) byteArrayOutputStreamArray[0]);
    }

    @Test
    public void testFlush() throws IOException {
        String content = "Hello world its a beautiful season";
        byte[] buffer = content.getBytes();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(new ByteArrayOutputStream(), 7);
        bufferedOutputStream.write(buffer, 1, 5);
        bufferedOutputStream.flush();
    }

    @Test
    public void testClose() throws IOException {
        String content = "Hello world its a beautiful season";
        byte[] buffer = content.getBytes();
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(new ByteArrayOutputStream(), 7);
        bufferedOutputStream.write(buffer, 1, 5);
        bufferedOutputStream.close();
        assertThrows(IOException.class, ()-> {
            bufferedOutputStream.write(buffer, 1, 5);
        });
        bufferedOutputStream.flush();
    }
}