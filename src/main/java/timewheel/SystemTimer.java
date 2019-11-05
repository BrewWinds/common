package timewheel;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Date: 2019/6/12 11:06
 * @Description:
 */
public class SystemTimer implements Timer{

    private String executorName;
    private Long tickMs;
    private Integer wheelSize;
    private Long startMs;

    private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();
    private AtomicInteger taskCounter = new AtomicInteger(0);
    private TimingWheel timingWheel = new TimingWheel(tickMs, wheelSize, startMs, taskCounter, delayQueue);

    private ExecutorService taskExecutor = Executors.newFixedThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread =  new Thread(r, "executor-"+executorName);
            thread.setDaemon(false);
            return thread;
        }
    });

    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    public SystemTimer(String executorName, Long tickMs,
                       Integer wheelSize, Long startMs){
        this.executorName = executorName;
        this.tickMs = tickMs == null ? 1L : tickMs;
        this.wheelSize = wheelSize == null ? 20 : wheelSize;
        this.startMs = startMs == null ? System.currentTimeMillis() : startMs;
    }

    @Override
    public void add(TimerTask timerTask) {
        readLock.lock();
        try{
            TimerTaskEntry entry = new TimerTaskEntry(timerTask, timerTask.dealyMs + System.currentTimeMillis());
            addTimerTaskEntry(entry);
        }finally {
            readLock.unlock();
        }
    }

    private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry){
        // already expired or cancelled
        if(!timingWheel.add(timerTaskEntry)){
            if(!timerTaskEntry.cancelled()){
                taskExecutor.submit(timerTaskEntry.timerTask);
            }
        }
    }

    private void reinsert(TimerTaskEntry timerTaskEntry){
        addTimerTaskEntry(timerTaskEntry);
    }

    @Override
    public Boolean advanceClock(Long timeoutMs){
        try {
            TimerTaskList bucket = delayQueue.poll(timeoutMs, TimeUnit.MILLISECONDS);

            if(bucket!=null){
                // write lock, cause it will finally change the bucket in the TimingWheel
                writeLock.lock();
                try {
                    while (bucket != null) {
                        // update currentTime
                        timingWheel.advanceClock(bucket.getExpiraton());
                        bucket.flush((x) -> {
                            reinsert((TimerTaskEntry) x);
                        });
                        bucket = delayQueue.poll();
                    }
                }finally {
                    writeLock.unlock();
                }
                return true;
            }else{
                return false;
            }
        } catch (InterruptedException e) {
            return false;
        }
    }



    @Override
    public Integer size() {
        return taskCounter.get();
    }

    @Override
    public void shutdown() {
        taskExecutor.shutdown();
    }

}
