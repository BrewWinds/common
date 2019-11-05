package kafka.listener;

import com.sun.tools.javac.util.List;
import kafka.exception.KafkaConsumeRetryException;

/**
 * @Date: 2019/2/19 15:59
 * @Description:
 */
public interface MessageListener<T>{
    void onMessage(List<T> messages) throws KafkaConsumeRetryException;
}
