package heartbeatKafka;

import org.apache.kafka.common.errors.RetriableException;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Date: 2019/4/12 15:30
 * @Description:
 */
public class RequsetFuture<T> implements PollCondition{

    private static final Object INCOMPLETE_SENTINEL = new Object();
    private final AtomicReference<Object> result = new AtomicReference<>(INCOMPLETE_SENTINEL);
    private ConcurrentLinkedQueue<RequestFutureListener<T>> listeners = new ConcurrentLinkedQueue();
    private final CountDownLatch completeLatch = new CountDownLatch(1);

    public boolean isDone(){
        return result.get() != INCOMPLETE_SENTINEL;
    }

    public boolean awaitDone(long timeout, TimeUnit unit) throws InterruptedException{
        return completeLatch.await(timeout, unit);
    }

    public boolean succeeded(){
        return isDone() && !failed();
    }

    public boolean failed(){
        return result.get() instanceof RuntimeException;
    }

    public boolean isRetriable(){
        return exception() instanceof RetriableException;
    }


    public RuntimeException exception(){
        if(!failed()){
            throw new IllegalStateException("Attempt to retrieve exception from future which hasn't failed");
        }
        return (RuntimeException) result.get();
    }


    public void raise(RuntimeException e){
        try{
            if( e== null){
                throw new IllegalArgumentException("The exception passed to raise must not be null");
            }

            if(!result.compareAndSet(INCOMPLETE_SENTINEL, e))
                throw new IllegalStateException("Invalid attempt to complete a request future which is already complete");

            fireFailure();

        }finally{
            completeLatch.countDown();
        }
    }

//    public void raise(Errors)


    public T value(){
        if(!succeeded())
            throw new IllegalStateException("Attempt to retrieve value from future which hasn't successfully completed");
        return (T) result.get();
    }

    public void complete(T value){
        try{
            if(value instanceof Runtime)
                throw new IllegalArgumentException("The argument to complete can not be an instance of RuntimeException");

            if(!result.compareAndSet(INCOMPLETE_SENTINEL, value))
                throw new IllegalStateException("Invalid attempt to complete a request future which is already complete");


        }finally{
            completeLatch.countDown();
        }
    }

    private void fireSuccess(){
        T value = value();
        while(true){
            RequestFutureListener<T> listener = listeners.poll();
            if(listener == null)
                break;
            listener.onSuccess(value);
        }
    }

    public void addListener(RequestFutureListener<T> listener){
        this.listeners.add(listener);
        if (failed())
            fireFailure();
        else if (succeeded())
            fireSuccess();
    }

    private void fireFailure(){
        RuntimeException exception = exception();
        while(true){
            RequestFutureListener<T> listener = listeners.poll();
            if(listener == null){
                break;
            }
            listener.onFailure(exception);
        }
    }

    @Override
    public boolean shouldBlock() {
        return !isDone();
    }
}
