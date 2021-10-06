package org.ogorodnik.IO;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {
    private final static int DEFAULT_ARRAY_SIZE = 32;
    InputStream target;
    byte[] buf;
    int bufIndex = 0;
    int count = 0;
    boolean isClosed = false;

    public BufferedInputStream(InputStream target) {
        this(target, DEFAULT_ARRAY_SIZE);
    }

    public BufferedInputStream(InputStream target, int size) {
        this.target = target;
        buf = new byte[size];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        //в оригинальном методе если в буфере осталось меньше байт, чем нужно вычитать,
        //то программа вычитает только оставшееся количество бай и загрузит следующую порцию
        //уже при следующем вызове
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
            if ((bufIndex == 0) && (len > (buf.length - 1))) {
                localCounter = target.read(b, off, len);
                return localCounter;
            } else {
                if ((bufIndex == 0) && (count == 0)) {
                    count = target.read(buf);
                }
                if (count == -1) {
                    return -1;
                } else {
                    int differenceBetweenBufLengthAndBufferIndex = count - bufIndex;
                    if ((count + differenceBetweenBufLengthAndBufferIndex) > len) {
                        for (int i = 0; i < len; i++) {
                            b[off] = buf[bufIndex];
                            bufIndex++;
                            off++;
                            localCounter++;
                            if (bufIndex == count) {
                                count = target.read(buf);
                            }
                            if (count == -1) {
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < differenceBetweenBufLengthAndBufferIndex; i++) {
                            b[off] = buf[bufIndex];
                            bufIndex++;
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
        if (bufIndex == count) {
            count = target.read(buf);
            bufIndex = 0;
        }
        if (count == -1) {
            return -1;
        } else {
            byte element = buf[bufIndex];
            bufIndex++;
            return element;
        }
    }
}
