package export;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @Date: 2019/8/20 14:57
 * @Description:
 */
public class CsvExporter extends AbstractCsvExporter<String>{

    public CsvExporter(){
        this(1024);
    }

    public CsvExporter(int capacity){
        super(new StringBuilder(capacity));
    }

    public CsvExporter(int capacity, char delimiter){
        super(new StringBuilder(capacity), delimiter);
    }

    @Override
    public String export() {
        return csv.toString();
    }

    @Override
    public void close() throws IOException {
        ((StringBuilder)super.csv).setLength(0);
    }

    public void write(String filepath, Charset charset, boolean withBom){
        File file = new File(filepath);
        try (WrappedBufferedWriter writer = new WrappedBufferedWriter(file, charset)) {
            if (withBom) {
                writer.write(WIDOWNS_BOM);
            }
            writer.append((StringBuilder) super.csv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        CsvExporter exporter = new CsvExporter();

        String[] he = new String[]{"id", "name", "age", "address"};
        List<String> header = Arrays.asList(he);

        exporter.setName("csv测试下载");

        Table table = new Table(header);

        new Thread(() -> {
            for(int i=0; i<100000; i++){
                table.addRow(new String[]{i+"", "name"+i, (i%100)+"", "address "+1});
            }
            try {
                Thread.sleep(3000);
                table.end();
            } catch (InterruptedException e) {
            }
        }).start();

        exporter.build(table);

        File f = new File("D:\\a.csv");

        exporter.write("D:\\a.csv", StandardCharsets.UTF_8, true);
    }
}
