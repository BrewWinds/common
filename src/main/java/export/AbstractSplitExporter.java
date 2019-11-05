package export;

import util.Holder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date: 2019/8/19 17:20
 * @Description:
 */
public abstract class AbstractSplitExporter extends AbstractExporter<Void>{

    private final int batchSize;
    private final String filePathPrefix;
    private final String fileSuffix;
    private final ExecutorService executor;
    public static final int AWAIT_TIME_MILLIS = 31;

    public AbstractSplitExporter(int batchSize, String filePathPrefix, String fileSuffix, ExecutorService executor) {
        this.batchSize = batchSize;
        this.filePathPrefix = filePathPrefix;
        this.fileSuffix = fileSuffix;
        this.executor = executor;
    }

    @Override
    public void build(Table table) {
        CompletionService<Void> service = new ExecutorCompletionService<>(executor);
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger split = new AtomicInteger(0);
        Holder<Table> subTable = Holder.of(table.copyOfWithoutTbody());

        rollingTbody(table, (data, i)->{
            subTable.get().addRow(data);
            if(count.incrementAndGet() == batchSize){
                super.nonEmpty();
                Table st = subTable.set(table.copyOfWithoutTbody());
                count.set(0);
                service.submit(splitExporter(st, buildFilePath(split.incrementAndGet())));
            }
        });
        if(!subTable.get().isEmptyTbody()){
            super.nonEmpty();;
            service.submit(splitExporter(subTable.get(), buildFilePath(split.incrementAndGet())));
        }

        int round = split.get();
        try{
            while (round > 0) {
                Future future = service.poll();
                if (future != null) {
                    round--;
                    //accept.accept(future.get());
                } else {
                    Thread.sleep(AWAIT_TIME_MILLIS);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildFilePath(int fileNo){
        return filePathPrefix + fileNo + fileSuffix;
    }

    public @Override final Void export() {
        throw new UnsupportedOperationException();
    }

    public @Override final void close() {}



    protected abstract AsyncSplitExporter splitExporter(Table subTable, String filePath);

    public static abstract class AsyncSplitExporter implements Callable<Void>{

        private final Table subTable;
        protected final String filePath;

        public AsyncSplitExporter(Table subTable, String filePath){
            this.subTable = subTable;
            this.filePath = filePath;
        }

        @Override
        public Void call() throws Exception {
            subTable.end();
            try(AbstractExporter<?> exporter = createExporter()){
                exporter.build(subTable);
                doOthers(exporter);
            }
            return null;
        }

        protected abstract AbstractExporter<?> createExporter();

        protected void doOthers(AbstractExporter<?> exporter){}
    }



}
