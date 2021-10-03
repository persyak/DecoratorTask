package org.ogorodnik.IO;

import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {
    private final static int DEFAULT_ARRAY_SIZE = 32;
    OutputStream target;
    byte[] buf;
    int bufIndex = 0;
    boolean isClosed = false;

    public BufferedOutputStream(OutputStream target) {
        this(target, DEFAULT_ARRAY_SIZE);
    }

    public BufferedOutputStream(OutputStream target, int size) {
        this.target = target;
        buf = new byte[size];
    }

    @Override
    public void write(int b) throws IOException {
        if (isClosed) {
            throw new IOException("Stream closed");
        }
        if (bufIndex == buf.length) {
            target.write(buf);
            bufIndex = 0;
        }
        buf[bufIndex] = (byte) b;
        bufIndex++;
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (isClosed) {
            throw new IOException("Stream closed");
        }
        if (b == null) {
            throw new NullPointerException("target array is null");
        } else if ((off < 0) || (len < 0) || (len > (b.length - off))) {
            throw new IndexOutOfBoundsException(
                    "off or len is less than zero or len is greater than b length minus off");
        } else {
            if (len > buf.length) {
                target.write(b, off, len);
            } else {
                if (len > (buf.length - bufIndex)) {
                    target.write(buf, 0, bufIndex);
                    bufIndex = 0;
                }
                for (int i = 0; i < len; i++) {
                    buf[bufIndex] = b[off];
                    bufIndex++;
                    off++;
                    if (bufIndex == buf.length) {
                        target.write(buf);
                        bufIndex = 0;
                    }
                }
            }
        }
    }

    @Override
    public void flush() throws IOException {
        //it can be called even after Stream is closed
        target.write(buf, 0, bufIndex);
        bufIndex = 0;
    }

    @Override
    public void close() throws IOException {
        target.write(buf, 0, bufIndex);
        bufIndex = 0;
        isClosed = true;
    }
}
