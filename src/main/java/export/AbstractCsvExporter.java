package export;

import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: 01378178
 * @Date: 2019/8/19 18:29
 * @Description:
 */
public abstract class AbstractCsvExporter<T> extends AbstractExporter<T> {

    static final byte[] WIDOWNS_BOM = {
        (byte) 0xEF, (byte) 0xBB, (byte) 0xBF
    };

    protected final Appendable csv;
    private final char separator;
    private volatile boolean build = false;
    private static final String LINE_SEPARATOR = "\r\n";

    public AbstractCsvExporter(Appendable csv){
        this(csv, ',');
    }

    public AbstractCsvExporter(Appendable csv, char separator){
        this.csv = csv;
        this.separator = separator;
    }


    @Override
    public void build(Table table) {
        if(build){
            throw new UnsupportedOperationException("support single table only");
        }

        build = true;
        List<String> thead = table.getThead();
        if(CollectionUtils.isEmpty(thead)){
            throw new IllegalArgumentException("thead cannot be null.");
        }


        // build table thead
        buildComplexThead(thead);

        // tbody
        rollingTbody(table, (data, i)->{
            try {
                for(int m = data.length-1, j=0; j<=m; j++){
                    csv.append(String.valueOf(data[j]));
                    if(j<m){
                        csv.append(separator);
                    }
                }
                csv.append(LINE_SEPARATOR);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        });

        try {
            if (table.isEmptyTbody()) {
                csv.append(NO_RESULT_TIP);
            } else {
                super.nonEmpty();
            }

            csv.append(LINE_SEPARATOR);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    private void buildComplexThead(List<String> thead){
        try {
            for(int i=0, n=thead.size(); i<n; i++){
                csv.append(thead.get(i));
                if(i!=n-1){
                    csv.append(separator);
                }
            }
            csv.append(LINE_SEPARATOR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
