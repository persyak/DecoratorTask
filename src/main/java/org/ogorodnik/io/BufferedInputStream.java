package org.ogorodnik.io;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {
    private final static int DEFAULT_ARRAY_SIZE = 32;
    private InputStream target;
    private byte[] buffer;
    private int bufferIndex = 0;
    private int count = 0;
    private boolean isClosed = false;

    public BufferedInputStream(InputStream target) {
        this(target, DEFAULT_ARRAY_SIZE);
    }

    public BufferedInputStream(InputStream target, int size) {
        this.target = target;
        buffer = new byte[size];
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (isClosed) {
            throw new IOException("Stream closed");
        }
        if (b == null) {
            throw new NullPointerException("target array is null");
        } else if ((off < 0) || (len < 0) || (len > (b.length - off))) {
            throw new IndexOutOfBoundsException(
                    "off or len is less than zero or len is greater than b length minus off");
        }
        int localCounter = 0;
        if ((bufferIndex == 0) && (len > (buffer.length - 1))) {
            localCounter = target.read(b, off, len);
            return localCounter;
        } else {
            if ((bufferIndex == 0) && (count == 0)) {
                count = target.read(buffer);
            }
            if (count == -1) {
                return -1;
            } else {
                int differenceBetweenBufferLengthAndBufferIndex = count - bufferIndex;
                if (differenceBetweenBufferLengthAndBufferIndex >= len) {
                    System.arraycopy(buffer, bufferIndex, b, off, len);
                    bufferIndex += len;
                    localCounter += len;
                } else {
                    System.arraycopy(buffer, bufferIndex, b, off, differenceBetweenBufferLengthAndBufferIndex);
                    localCounter += differenceBetweenBufferLengthAndBufferIndex;
                    off += differenceBetweenBufferLengthAndBufferIndex;
                    bufferIndex = 0;
                    int residue = len - differenceBetweenBufferLengthAndBufferIndex;
                    if (residue > (buffer.length - 1)) {
                        localCounter += target.read(b, off, residue);
                    } else {
                        count = target.read(buffer);
                        if (count == -1) {
                            return localCounter;
                        } else {
                            if (count > residue) {
                                System.arraycopy(buffer, bufferIndex, b, off, residue);
                                localCounter += residue;
                                bufferIndex += residue;
                            } else {
                                System.arraycopy(buffer, bufferIndex, b, off, count);
                                localCounter += count;
                                bufferIndex = 0;
                                count = 0;
                            }
                        }
                    }
                }
                return localCounter;
            }
        }
    }

    @Override
    public void close() {
        isClosed = true;
    }

    @Override
    public int read() throws IOException {
        if (isClosed) {
            throw new IOException("Stream closed");
        }
        if (bufferIndex == count) {
            count = target.read(buffer);
            bufferIndex = 0;
        }
        if (count == -1) {
            return -1;
        } else {
            byte element = buffer[bufferIndex];
            bufferIndex++;
            return element;
        }
    }
}
