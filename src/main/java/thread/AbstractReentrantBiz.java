package thread;

import org.apache.kafka.common.PartitionInfo;

import java.time.Duration;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Date: 2019/4/16 10:44
 * @Description:
 */
public class AbstractReentrantBiz {

    private static final long NO_CURRENT_THREAD = -1;

    private final AtomicLong currentThread = new AtomicLong(1);
    private final AtomicInteger refcount = new AtomicInteger(0);

    private volatile boolean closed = false;

    private void acquire(){
        long threadId = Thread.currentThread().getId();
        if(threadId != currentThread.get() && currentThread.compareAndSet(NO_CURRENT_THREAD, threadId)){
            throw new ConcurrentModificationException("Not safe for multi-thread access");
        }
        refcount.incrementAndGet();
    }

    private void release(){
        if(refcount.decrementAndGet() == 0){
            currentThread.set(NO_CURRENT_THREAD);
        }
    }

    private void acquireAndEnsureOpen(){
        acquire();
        if(this.closed){
            release();
            throw new IllegalStateException("Has already been closed");
        }
    }

    public void close(Duration timeout){
        if(timeout.toMillis() < 0){
            throw new IllegalArgumentException("The timeout cannot be negative");
        }
        acquire();
        try{
            if(!closed){
                closed = true;
                close(timeout.toMillis(), false);
            }
        }finally{
            release();
        }
    }

    public void close(long timeoutMs, boolean swallowException){

    }


    public static class PartitionInfoAndEpoch{
        private final PartitionInfo partitionInfo;
        private final int epoch;

        public PartitionInfoAndEpoch(PartitionInfo partitionInfo, int epoch) {
            this.partitionInfo = partitionInfo;
            this.epoch = epoch;
        }

        public PartitionInfo getPartitionInfo() {
            return partitionInfo;
        }

        public int getEpoch() {
            return epoch;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PartitionInfoAndEpoch that = (PartitionInfoAndEpoch) o;
            return epoch == that.epoch &&
                    Objects.equals(partitionInfo, that.partitionInfo);
        }

        @Override
        public int hashCode() {

            return Objects.hash(partitionInfo, epoch);
        }
    }
}
