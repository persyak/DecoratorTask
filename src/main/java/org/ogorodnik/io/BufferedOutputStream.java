package org.ogorodnik.io;

import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {
    private final static int DEFAULT_ARRAY_SIZE = 32;
    private OutputStream target;
    private byte[] buffer;
    private int bufferIndex = 0;
    private boolean isClosed = false;

    public BufferedOutputStream(OutputStream target) {
        this(target, DEFAULT_ARRAY_SIZE);
    }

    public BufferedOutputStream(OutputStream target, int size) {
        this.target = target;
        buffer = new byte[size];
    }

    @Override
    public void write(int b) throws IOException {
        if (isClosed) {
            throw new IOException("Stream closed");
        }
        if (bufferIndex == buffer.length) {
            target.write(buffer);
            bufferIndex = 0;
        }
        buffer[bufferIndex] = (byte) b;
        bufferIndex++;
    }

    @Override
    public void write(byte[] b, int off, int length) throws IOException {
        if (isClosed) {
            throw new IOException("Stream closed");
        }
        if (b == null) {
            throw new NullPointerException("target array is null");
        } else if ((off < 0) || (length < 0) || (length > (b.length - off))) {
            throw new IndexOutOfBoundsException(
                    "off or length is less than zero or length is greater than b length minus off");
        }
        if (length >= buffer.length) {
            flush();
            target.write(b, off, length);
        } else {
            if (length > (buffer.length - bufferIndex)) {
                flush();
                System.arraycopy(b, off, buffer, bufferIndex, length);
                bufferIndex += length;
            } else {
                System.arraycopy(b, off, buffer, bufferIndex, length);
                bufferIndex += length;
            }
        }
    }

    @Override
    public void flush() throws IOException {
        //it can be called even after Stream is closed
        target.write(buffer, 0, bufferIndex);
        bufferIndex = 0;
    }

    @Override
    public void close() throws IOException {
        target.write(buffer, 0, bufferIndex);
        bufferIndex = 0;
        isClosed = true;
    }

    byte[] getBuffer() {
        return buffer;
    }

    int getIndex() {
        return bufferIndex;
    }
}
