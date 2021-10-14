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
    public int read(byte[] b, int off, int length) throws IOException {
        int localCounter = 0;
        if (b == null) {
            throw new NullPointerException("target array is null");
        } else if ((off < 0) || (length < 0) || (length > (b.length - off))) {
            throw new IndexOutOfBoundsException(
                    "off or length is less than zero or length is greater than b length minus off");
        }
        int spaceInBuffer = bytes.length - position;
        if (length < spaceInBuffer) {
            System.arraycopy(bytes, position, b, off, length);
            position += length;
            off += length;
            localCounter += length;
        } else if (spaceInBuffer == 0) {
            return -1;
        } else {
            System.arraycopy(bytes, position, b, off, spaceInBuffer);
            position += spaceInBuffer;
            off += spaceInBuffer;
            localCounter += spaceInBuffer;
        }
        return localCounter;
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
