package org.ogorodnik.IO;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {
    private byte[] bytes;
    int position = 0;

    public ByteArrayInputStream(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return super.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return super.read(b, off, len);
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public int read() {
        byte element;
        if (position < bytes.length) {
            element = bytes[position];
            position++;
        } else{
            element = -1;
        }
        return element;
    }
}
