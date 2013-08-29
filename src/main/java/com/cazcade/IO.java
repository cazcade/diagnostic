package com.cazcade;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public final class IO {

    public void copy(File source, File target) throws IOException {
        FileChannel in = new FileInputStream(source).getChannel();
        FileChannel out = new FileOutputStream(target).getChannel();

// JavaVM does its best to do this as native I/O operations.
        in.transferTo (0, in.size(), out);

// Closing file channels will close corresponding stream objects as well.
        out.close();
        in.close();
    }

    public static void fastChannelCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            // prepare the buffer to be drained
            buffer.flip();
            // write to the channel, may block
            dest.write(buffer);
            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear()
            buffer.compact();
        }
        // EOF will leave buffer in fill state
        buffer.flip();
        // make sure the buffer is fully drained.
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    public static void copy(InputStream input, OutputStream output) throws IOException {
        try (final ReadableByteChannel inputChannel = Channels.newChannel(input);
             final WritableByteChannel outputChannel = Channels.newChannel(output);) {
// copy the channels
            IO.fastChannelCopy(inputChannel, outputChannel);
// closing the channels
        }
    }
}
