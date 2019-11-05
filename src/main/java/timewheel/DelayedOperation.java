package timewheel;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 01378178
 * @Date: 2019/6/12 16:34
 * @Description:
 */
public abstract class DelayedOperation extends TimerTask {


    private Lock lock;
    private AtomicBoolean completed = new AtomicBoolean(false);
    private AtomicBoolean tryCompletePending = new AtomicBoolean(false);


    public DelayedOperation(long delayMs, Lock lock) {
        this.lock = lock == null ? new ReentrantLock() : lock;
    }

    Boolean forceComplete() {
        if (completed.compareAndSet(false, true)) {
            cancel();
            onComplete();
            return true;
        } else {
            return false;
        }
    }

    public abstract void onExpiration();

    public abstract void onComplete();

    public abstract Boolean tryComplete();

    public Boolean isCompleted() {
        return completed.get();
    }

    public Boolean maybeTryComplete() {
        boolean retry = false;
        boolean done = false;
        do {
            if (lock.tryLock()) {
                try {
                    tryCompletePending.set(false);
                    done = tryComplete();
                } finally {
                    lock.unlock();
                }
                retry = tryCompletePending.get();
            } else {
                retry = !tryCompletePending.getAndSet(true);
            }
        } while (!isCompleted() && retry);
        return done;
    }

    public void run(){
        if(forceComplete()){
            onExpiration();
        }
    }
}
class DelayedOperationPurgatory{

}