package export;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * @Date: 2019/8/19 18:29
 * @Description:
 */
public class CsvFileExporter extends AbstractCsvExporter<Void>{

    public static final int BUFF_SIZE = 8192;

    public CsvFileExporter(String filePath, boolean withBom){
        this(new File(filePath), StandardCharsets.UTF_8, withBom);
    }

    public CsvFileExporter(File file, Charset charset, boolean withBom){
        super(createWriter(file, charset, withBom), ',');
    }

    public static Appendable createWriter(File file, Charset charset, boolean withBom){
        try {
            WrappedBufferedWriter writer = new WrappedBufferedWriter(file, charset);
            if(withBom){
                writer.write(WIDOWNS_BOM);
            }
            return writer;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Void export() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        ((WrappedBufferedWriter)super.csv).close();
    }
}