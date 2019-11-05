package export;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @Date: 2019/8/20 14:33
 * @Description:
 */
public class WrappedBufferedWriter extends Writer {

    private OutputStream output;
    private OutputStreamWriter writer;
    private BufferedWriter buffer;
    public static final int BUFF_SIZE = 8192;

    public WrappedBufferedWriter(File file) throws FileNotFoundException {
        this(file, Charset.defaultCharset());
    }

    public WrappedBufferedWriter(File file, Charset charset) throws FileNotFoundException {
        this(new FileOutputStream(file), charset);
    }

    public void write(byte[] bytes) throws IOException {
        output.write(bytes);
    }

    public WrappedBufferedWriter(OutputStream output, Charset charset){
        super();
        this.output = output;
        this.writer = new OutputStreamWriter(output, charset);
        this.buffer = new BufferedWriter(writer, 8192);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        buffer.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        buffer.flush();
        writer.flush();
        output.flush();
    }

    @Override
    public void close() throws IOException {
        if (buffer != null) try {
            buffer.close();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        buffer = null;

        if (writer != null) try {
            writer.close();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        writer = null;

        if (output != null) try {
            output.close();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        output = null;
    }
}
