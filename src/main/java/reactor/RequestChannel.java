package reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 01378178
 * @Date: 2019/7/1 10:30
 * @Description:
 */
public class RequestChannel {


    private Logger logger = LoggerFactory.getLogger(RequestChannel.class);

    private final int queueSize;

    private ArrayBlockingQueue<Request> requestQueue;
    private ConcurrentHashMap<Integer, Processor> processors = new ConcurrentHashMap<>();



    public RequestChannel(int queueSize, String metricNamePrefix){
        this.queueSize = queueSize;
        requestQueue = new ArrayBlockingQueue(queueSize);
    }

    public void addProcessor(Processor processor){
        if(processors.putIfAbsent(processor.id, processor)!=null){
            logger.warn("Unexpected processor with processorId {}", processor.id);
        }
    }

    public void removeProcessor(Integer processorId){
        processors.remove(processorId);
        // removeMetric
    }

    public void sendRequest(Request request) throws InterruptedException {
        requestQueue.put(request);
    }


    public void sendResponse(Response response){
        Processor processor = processors.get(response.processor);
        if(processor!=null){
            processor.enqueueResponse(response);
        }
    }

    public Request sendResponse(Long timeout) throws InterruptedException {
        return requestQueue.poll(timeout, TimeUnit.MILLISECONDS);
    }

    public Request receiveRequest() throws InterruptedException {
        return requestQueue.take();
    }

    public void clear(){
        requestQueue.clear();
    }

    public void shutdown(){
        clear();
    }

    abstract class BaseRequest{
    }

    public class Request extends BaseRequest{
        public Request(Integer processor, RequestContext context, Long startTimeNanos, MemoryPool memoryPool){

        }
    }

    abstract class Response{
        public Processor processor;
    }
}
