package importer.extractor;

import export.CsvExporter;
import export.DataExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Date: 2019/8/20 16:00
 * @Description:
 */
public class CsvExtractor<T> extends DataExtractor<T> {

    private final CSVFormat csvFormat;
    private final boolean specHeaderss;

    public CsvExtractor(Object dataSource, String[] headers){
        this(dataSource, headers, null);
    }

    public CsvExtractor(Object dataSource, String[] headers, CSVFormat csvFormat){
        super(dataSource, headers);
        this.specHeaderss = ArrayUtils.isNotEmpty(headers);
        this.csvFormat = Optional.ofNullable(csvFormat).orElse(CSVFormat.DEFAULT);
        if(this.specHeaderss){
            this.csvFormat.withHeader(headers);
        }
    }

    @Override
    public void extract(RowProcessor<T> processor) throws IOException {
        try(InputStream is = asInputStream();
            BOMInputStream bom = new BOMInputStream(is);
            Reader reader = new InputStreamReader(bom)){

            int colSize = specHeaderss ? this.headers.length : 0;
            Iterable<CSVRecord> records = csvFormat.parse(reader);
            int i = 0, j, n;
            T data;
            for(CSVRecord record : records){
                if(!specHeaderss && i==0){
                    colSize = record.size();
                }
                n = record.size();
                if(colSize == 1){
                    if(n==0){
                        data = (T) StringUtils.EMPTY;
                    }else{
                        data = (T) record.get(0);
                    }
                }else{
                    String[] array = new String[colSize];
                    for(j=0; j<n && j<colSize; j++){
                        array[j] = record.get(j);
                    }
                    for(; j<colSize; j++){
                        array[j] = StringUtils.EMPTY;
                    }
                    data = (T) array;
                }
                if(isNotEmpty(data)){
                    processor.process(i++, data);
                }

            }

        }
    }

}
