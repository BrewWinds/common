package kafka.consumer;

import kafka.ConsumeExceptionHandler;
import kafka.listener.MessageListener;
import kafka.model.ConsumerOptionalConfig;
import kafka.model.KafkaBatchMessage;
import kafka.model.KafkaMessage;
import thread.RepeatWorkThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Date: 2019/2/20 17:49
 * @Description:
 */
public class KafkaConsumer {

    private static final int MAX_BATCH_MSG_SIZE = 1000;
    private static final long DEFAULT_HANDLE_INTERVAL_MS = 5000;

    private BlockingQueue<KafkaMessage> orgMsgs = new ArrayBlockingQueue<>(1000);
    private BlockingQueue<KafkaBatchMessage> batchMsg = new ArrayBlockingQueue<>(100);

    private ConsumerInnerConfig innerCfg;
    private ConsumerOptionalConfig optionalCfg;

    public KafkaConsumer(ConsumerOptionalConfig optionalConfig, MessageListener listener){
        prepation();
        monitorRegister();
        init();
    }
    public void prepation(){}
    public void monitorRegister(){}

    public void init(){
    }

    private class DataThread extends RepeatWorkThread{

        @Override
        protected void repeatWork() throws InterruptedException {

        }
    }

    private class BatchDataThread extends RepeatWorkThread{

        @Override
        protected void repeatWork() throws InterruptedException {

        }
    }

    /**
     * the actual logic to handle the message consumer
     */
    private class ConsumeTask implements Runnable{

        private MessageListener listener;
        private ConsumeExceptionHandler exceptionHandler;

        /**
         * 内部类, 引用主类的对象. 注意对象回收
         */
        public ConsumeTask(){
            this.listener = KafkaConsumer.this.innerCfg.listener;
            this.exceptionHandler = exceptionHandler;
        }

        @Override
        public void run() {

        }
    }


    public void addToOrgQueue(KafkaMessage msg){

    }

    public void addToBatchQueue(KafkaBatchMessage batchMsg){

    }

    public void beforeAddToQueue(KafkaMessage msg){
    }

    public void completeMsg(KafkaBatchMessage batchMsg){
    }
}
