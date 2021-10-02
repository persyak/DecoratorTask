package org.ogorodnik.IO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {
    private final static int DEFAULT_ARRAY_SIZE = 32;
    OutputStream target;
    byte[] buf;
    int bufIndex = 0;
    int count = 0;

    public BufferedOutputStream(OutputStream target){
        this(target, DEFAULT_ARRAY_SIZE);
    }

    public BufferedOutputStream(OutputStream target, int size){
        this.target = target;
        buf = new byte[size];
    }

    @Override
    public void write(int b) throws IOException {
        if(bufIndex == buf.length){
            target.write(buf);
            bufIndex = 0;
        }
        buf[bufIndex] = (byte)b;
        bufIndex++;
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
