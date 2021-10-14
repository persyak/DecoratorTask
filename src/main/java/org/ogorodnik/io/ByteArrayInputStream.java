package org.ogorodnik.io;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {
    private byte[] bytes;
    private int position = 0;

    public ByteArrayInputStream(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int localCounter = 0;
        if (b == null) {
            throw new NullPointerException("target array is null");
        } else if ((off < 0) || (len < 0) || (len > (b.length - off))) {
            throw new IndexOutOfBoundsException(
                    "off or len is less than zero or len is greater than b length minus off");
        } else {
            if (len < (bytes.length - position)) {
                for (int i = 0; i < len; i++) {
                    b[off] = bytes[position];
                    position++;
                    off++;
                    localCounter++;
                }
            } else if ((bytes.length - position) == 0) {
                return -1;
            } else {
                int countMinusPosition = bytes.length - position;
                for (int i = 0; i < countMinusPosition; i++) {
                    b[off] = bytes[position];
                    position++;
                    off++;
                    localCounter++;
                }
            }
            return localCounter;
        }
    }

    @Override
    public void close() throws IOException {
        //from description it does nothing
    }

    @Override
    public int read() {
        byte element;
        if (position < bytes.length) {
            element = bytes[position];
            position++;
        } else {
            element = -1;
        }
        return element;
    }
}
