package org.ogorodnik.IO;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {
    private final static int DEFAULT_ARRAY_SIZE = 32;
    InputStream target;
    byte[] buffer;

    public BufferedInputStream(InputStream target){
        this(target, DEFAULT_ARRAY_SIZE);
    }

    public BufferedInputStream(InputStream target, int size){
        this.target = target;
        buffer = new byte[size];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        target.read(b, off, len);

        return 0;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public int read() {
        return 0;
    }
}
