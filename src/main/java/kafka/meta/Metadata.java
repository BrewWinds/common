package kafka.meta;

import com.mchange.v1.util.ClosableResource;

import javax.xml.bind.Marshaller;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2019/4/19 15:48
 * @Description:
 */
public class Metadata implements Closeable {


//    private MetadataCache cache = MetadataCache.empty();
    private boolean needUpdate;
    private Map<String, Long> topics;

    @Override
    public void close() throws IOException {

    }
}
