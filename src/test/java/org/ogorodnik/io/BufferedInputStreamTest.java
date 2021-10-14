package org.ogorodnik.io;

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
        assertEquals(5, bufferedInputStream.read(buffer));
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
        assertEquals(4, bufferedInputStream.read(buffer, 1, 4));
        assertEquals(-1, bufferedInputStream.read(buffer, 1, 4));
    }

    @Test
    public void testClose() throws IOException {
        new File("TestData").mkdir();
        String path =  "TestData/File1.txt";
        String text = "TestData/DataForCount/File1.txt file";
        new File(path).createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        bufferedWriter.write(text);
        bufferedWriter.newLine();
        bufferedWriter.write(text);
        bufferedWriter.flush();
        bufferedWriter.close();
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream(fileInputStream, 1);
        assertEquals('T', (char) bufferedInputStream.read());
        fileInputStream.close();
        assertThrows(IOException.class, bufferedInputStream::read);
        new File("TestData/File1.txt").delete();
        new File("TestData").delete();
    }

    @Test
    public void testRead() throws IOException {
        String content = "Hello";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()));
        assertEquals('H', (char) bufferedInputStream.read());
        assertEquals('e', (char) bufferedInputStream.read());
        assertEquals('l', (char) bufferedInputStream.read());
        assertEquals('l', (char) bufferedInputStream.read());
        assertEquals('o', (char) bufferedInputStream.read());
        assertEquals(-1, bufferedInputStream.read());
    }

    @Test
    public void testReadWhenInputStreamArraySizeIsNotDefault() throws IOException {
        String content = "Hello";
        BufferedInputStream bufferedInputStream
                = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()), 4);
        assertEquals('H', (char) bufferedInputStream.read());
        assertEquals('e', (char) bufferedInputStream.read());
        assertEquals('l', (char) bufferedInputStream.read());
        assertEquals('l', (char) bufferedInputStream.read());
        assertEquals('o', (char) bufferedInputStream.read());
        assertEquals(-1, bufferedInputStream.read());
    }
}