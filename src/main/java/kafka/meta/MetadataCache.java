package kafka.meta;

import kafka.common.Cluster;
import kafka.common.Node;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Date: 2019/4/19 15:48
 * @Description:
 */
public class MetadataCache implements Closeable {
    @Override
    public void close() throws IOException {

    }

   /* private final String clusterId;
    private final List<Node> nodes;
    private final Set<String> unauthorizedTopic;
    private final Set<String> invalidTopics;
    private final Set<String> internalTopic;
    private final Node controller;
    private final Map<TopicPartition, PartitionInfoAndEpoch> metadataByPartition;


    Cluster cluster(){
        if(clusterInstance == null){
            throw new IllegalStateException("");
        }else{
            return clusterInstance
        }
    }

    private Cluster clusterInstance;

    @Override
    public void close() throws IOException {

    }

    public static class PartitionInfoAndEpoch {
        private final PartitionInfo partitionInfo;
        private final int epoch;

        public PartitionInfoAndEpoch(PartitionInfo partitionInfo, int epoch) {
            this.partitionInfo = partitionInfo;
            this.epoch = epoch;
        }
    }*/
}
