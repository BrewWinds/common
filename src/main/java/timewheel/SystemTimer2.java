package timewheel;

import util.Obj;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * @Auther: 01378178
 * @Date: 2019/6/14 13:56
 * @Description:
 */
public class SystemTimer2 {

    private Long tickMs;
    private Integer wheelSize;
    private Long startMs;
    private String executorName;

    private ExecutorService taskExetuor = Executors.newFixedThreadPool(1, (r)-> {
            Thread t = new Thread(r,"executor-"+executorName);
            t.setDaemon(false);
            return t;
    });

    DelayQueue<TimerTaskList> delayedQueue = new DelayQueue<>();
    AtomicInteger taskCounter = new AtomicInteger(0);
    TimingWheel timingWheel = new TimingWheel(tickMs, wheelSize, startMs, taskCounter, delayedQueue);

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock =  readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    public SystemTimer2(String executorName, Long tickMs, Integer wheelSize, Long startMs){
        this.executorName = executorName;
        this.tickMs = (Long) Obj.orElse(startMs, 1);
        this.wheelSize = (Integer) Obj.orElse(wheelSize, 20);
        this.startMs = (Long) Obj.orElse(startMs, System.currentTimeMillis());
    }

    public void add(TimerTask timerTask){
        readLock.lock();
        try{
            addTimerTaskEntry(new TimerTaskEntry(timerTask, timerTask.dealyMs));
        }finally {
            readLock.unlock();
        }
    }

    public void addTimerTaskEntry(TimerTaskEntry timerTaskEntry){
        if(!timingWheel.add(timerTaskEntry)){
            if(!timerTaskEntry.cancelled()){
                taskExetuor.submit(timerTaskEntry.timerTask);
            }
        }
    }

    public Boolean advanceClock(Long timeoutMs) throws InterruptedException {

        TimerTaskList bucket = delayedQueue.poll(timeoutMs, TimeUnit.MILLISECONDS);
        if(bucket!=null){
            writeLock.lock();
            try{
                while(bucket!=null){
                    timingWheel.advanceClock(bucket.getExpiraton());
                    bucket.flush((o)-> { reinsert((TimerTaskEntry) o); });
                    bucket = delayedQueue.poll();
                }
            }finally{
                writeLock.unlock();
            }
            return true;
        }else{
            return false;
        }

    }

    public void shutdown(){
        taskExetuor.shutdown();
    }

    public void reinsert(TimerTaskEntry timerTaskEntry){
        addTimerTaskEntry(timerTaskEntry);
    }

}
