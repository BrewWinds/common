package kafka.consumer;

import kafka.listener.MessageListener;

/**
 * @Date: 2019/2/20 16:35
 * @Description:
 */
public class ConsumerInnerConfig {

    String systemId;
    String zkServers;
    MessageListener listener;
    int msgConsumerThreadCount;

    public ConsumerInnerConfig(String systemId, String zkServers, MessageListener listener, int msgConsumerThreadCount) {
        this.systemId = systemId;
        this.zkServers = zkServers;
        this.listener = listener;
        this.msgConsumerThreadCount = msgConsumerThreadCount;
    }
}
