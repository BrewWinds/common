package export;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Auther: 01378178
 * @Date: 2019/8/19 16:40
 * @Description:
 */
public interface DataExporter<T> extends Closeable {

    String NO_RESULT_TIP = "data not found";

    void build(Table table);

    T export();

    boolean isEmpty();

    @Override
    void close() throws IOException;
}
