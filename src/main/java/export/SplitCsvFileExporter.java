package export;

import java.util.concurrent.ExecutorService;

/**
 * @Date: 2019/8/19 17:19
 * @Description:
 */
public class SplitCsvFileExporter extends AbstractSplitExporter{

    private final boolean withBom;

    public SplitCsvFileExporter(int batchSize, String pathPrefix,
                                boolean withBom, ExecutorService executor){
        super(batchSize, pathPrefix, ".csv", executor);
        this.withBom = withBom;
    }

    @Override
    protected AsyncSplitExporter splitExporter(Table subTable, String filePath) {
        return new AsyncCsvFileExporter(subTable, filePath, withBom);
    }

    private static class AsyncCsvFileExporter extends AsyncSplitExporter{

        final boolean withBom;

        AsyncCsvFileExporter(Table subTable, String filePath, boolean withBom){
            super(subTable, filePath);
            this.withBom = withBom;
        }

        @Override
        protected AbstractExporter<?> createExporter() {
            return new CsvFileExporter(filePath, withBom);
        }
    }
}
