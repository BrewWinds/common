package kafka.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 01378178
 * @Date: 2019/2/20 16:35
 * @Description:
 */
public class KafkaBatchMessage {

    private Map<Integer, Long> partitionOffset = new HashMap<>();
    private List<KafkaMessage> msgs = new ArrayList();


    /**
     * remove the whole partition.
     * @param partition
     */
    public void remove(Integer partition){
    }
}
