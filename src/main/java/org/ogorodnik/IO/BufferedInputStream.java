package org.ogorodnik.IO;

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
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
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
        } else {
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
                    int differenceBetweenBufLengthAndBufferIndex = count - bufferIndex;
                    if ((count + differenceBetweenBufLengthAndBufferIndex) > len) {
                        for (int i = 0; i < len; i++) {
                            b[off] = buffer[bufferIndex];
                            bufferIndex++;
                            off++;
                            localCounter++;
                            if (bufferIndex == count) {
                                count = target.read(buffer);
                                bufferIndex = 0;
                            }
                            if (count == -1) {
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < differenceBetweenBufLengthAndBufferIndex; i++) {
                            b[off] = buffer[bufferIndex];
                            bufferIndex++;
                            off++;
                            localCounter++;
                        }
                        localCounter += target.read(b, off, (len - differenceBetweenBufLengthAndBufferIndex));
                    }
                    return localCounter;
                }
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
