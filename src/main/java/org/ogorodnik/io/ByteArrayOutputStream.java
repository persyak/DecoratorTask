package org.ogorodnik.io;

import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayOutputStream extends OutputStream {

    private final static int DEFAULT_ARRAY_SIZE = 32;
    private byte[] bytes;
    private int position = 0;
//    bytes and position variables are protected in original class, it depends from needs. I leave them private;

    public ByteArrayOutputStream() {
        this(DEFAULT_ARRAY_SIZE);
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
        } else {
            for (int i = 0; i < length; i++) {
                if (position == (bytes.length - 1)) {
                    bytes = increase(bytes);
                }
                bytes[position] = b[off];
                position++;
                off++;
            }
        }
    }

    @Override
    public void flush() throws IOException {
        //from what it's seen in JavaDoc, this method does nothing except the case when it's used in
        //the end of output pipeline that includes a buffering component
    }

    @Override
    public void close() throws IOException {
        //Java doc sais it does nothing so the body of this method might be empty at all.
        // I guess it will be depricated soon or any implementation will be added.
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
