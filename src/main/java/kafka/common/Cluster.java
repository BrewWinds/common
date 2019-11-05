package kafka.common;

import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * @Date: 2019/4/19 15:57
 * @Description:
 */
public class Cluster {
    private final boolean isBootstrapConfigured;
    private final List<Node> nodes;
    private final Set<String> unauthorizedTopics;
    private final Set<String> invalidTopics;
    private final Set<String> internalTopics;
    private final Node controller;
    private final Map<TopicPartition, PartitionInfo> partitionsByTopicPartition;
    private final Map<String, List<PartitionInfo>> partitionsByTopic;
    private final Map<String, List<PartitionInfo>> availablePartitionsByTopic;
    private final Map<String, List<PartitionInfo>> partitionsByNode;
    private final Map<Integer, Node> nodesById;
    private final ClusterResource clusterResource;

    private Cluster(String clusterId,
                    boolean isBootstrapConfigured,
                    Collection<Node> nodes,
                    Collection<PartitionInfo> partitions,
                    Set<String> unauthorizedTopics,
                    Set<String> invalidTopics,
                    Set<String> internalTopics,
                    Node controller) {

        this.isBootstrapConfigured = isBootstrapConfigured;
        this.clusterResource = new ClusterResource(clusterId);
        // make a randomized, unmodifiable copy of the nodes
        List<Node> copy = new ArrayList<>(nodes);
        Collections.shuffle(copy);
        this.nodes = Collections.unmodifiableList(copy);

        // Index the nodes for quick lookup
        Map<Integer, Node> tmpNodesById = new HashMap<>();
        for (Node node : nodes)
            tmpNodesById.put(/*node.id()*/0, node);
        this.nodesById = Collections.unmodifiableMap(tmpNodesById);

        // index the partition infos by topic, topic+partition, and node
        Map<TopicPartition, PartitionInfo> tmpPartitionsByTopicPartition = new HashMap<>(partitions.size());
        Map<String, List<PartitionInfo>> tmpPartitionsByTopic = new HashMap<>();
        Map<String, List<PartitionInfo>> tmpAvailablePartitionsByTopic = new HashMap<>();
        Map<Integer, List<PartitionInfo>> tmpPartitionsByNode = new HashMap<>();
        for (PartitionInfo p : partitions) {
            tmpPartitionsByTopicPartition.put(new TopicPartition(p.topic(), p.partition()), p);
//            tmpPartitionsByTopic.merge(p.topic(), Collections.singletonList(p), Utils::concatListsUnmodifiable);
            if (p.leader() != null) {
//                tmpAvailablePartitionsByTopic.merge(p.topic(), Collections.singletonList(p), Utils::concatListsUnmodifiable);
//                tmpPartitionsByNode.merge(p.leader().id(), Collections.singletonList(p), Utils::concatListsUnmodifiable);
            }
        }
        this.partitionsByTopicPartition = Collections.unmodifiableMap(tmpPartitionsByTopicPartition);
        this.partitionsByTopic = Collections.unmodifiableMap(tmpPartitionsByTopic);
        this.availablePartitionsByTopic = Collections.unmodifiableMap(tmpAvailablePartitionsByTopic);
        this.partitionsByNode = /*Collections.unmodifiableMap(tmpPartitionsByNode);*/ null;

        this.unauthorizedTopics = Collections.unmodifiableSet(unauthorizedTopics);
        this.invalidTopics = Collections.unmodifiableSet(invalidTopics);
        this.internalTopics = Collections.unmodifiableSet(internalTopics);
        this.controller = controller;
    }
}
