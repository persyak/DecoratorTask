package org.ogorodnik.IO;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {
    InputStream target;

    BufferedInputStream(InputStream inputStream){
        this.target = inputStream;
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
        return 0;
    }
}
