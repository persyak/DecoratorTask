package org.ogorodnik.io;

import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayOutputStream extends OutputStream {

    private final static int DEFAULT_CAPACITY = 32;
    private byte[] bytes;
    private int position = 0;
//    bytes and position variables are protected in original class, it depends from needs. I leave them private;

    public ByteArrayOutputStream() {
        this(DEFAULT_CAPACITY);
    }

    public ByteArrayOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Array size can't be less than 0");
        }
        bytes = new byte[size];
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int length) throws IOException {
        if (b == null) {
            throw new NullPointerException("source array is null");
        } else if ((off < 0) || (length < 0) || (length > (b.length - off))) {
            throw new IndexOutOfBoundsException(
                    "off or length is less than zero or length is greater than b length minus off");
        }
            int spaceInBuffer = bytes.length - 1 - position;
            if (length > spaceInBuffer) {
                bytes = increase(bytes);
            }
            System.arraycopy(b, off, bytes, position, length);
            position += length;
    }

    @Override
    public void write(int b) throws IOException {
        //what is very unusual for me is that original method cast any int value to byte
        // and does not check if it's bigger than byte
        if (position == (bytes.length - 1)) {
            bytes = increase(bytes);
        }
        bytes[position] = (byte) b;
        position++;
    }

    private byte[] increase(byte[] array) {
        byte[] newBytesArray = new byte[(int) ((1.5 * array.length) + 1)];
        System.arraycopy(array, 0, newBytesArray, 0, array.length);
        array = newBytesArray;
        return array;
    }

    int getPosition() {
        return position;
    }

    byte[] getBytes() {
        return bytes;
    }

}
