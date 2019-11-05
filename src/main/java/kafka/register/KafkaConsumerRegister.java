package kafka.register;

import com.google.common.collect.Sets;
import kafka.listener.MessageListener;
import kafka.model.ConsumerOptionalConfig;

import java.util.Set;

/**
 * @Date: 2019/2/19 16:03
 * @Description:
 */
public class KafkaConsumerRegister {

    private static final Set<String> topics = Sets.newHashSet();

    public KafkaConsumerRegister(){}

    private static <Message> boolean register(ConsumerOptionalConfig config, MessageListener listener){
        // cache topic

        // new Consumer();

        return false;
    }

}
