package hearbeat;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date: 2019/1/8 10:37
 * @Description:
 */
public class HeartBeat {

    private final ThreadPoolExecutor executor;

    private boolean started = false;
    private HeartBeatRunner heartBeat;

    public HeartBeat(ThreadPoolTaskExecutor executor){
        this.executor = executor.getThreadPoolExecutor();
    }

    public synchronized void startup(){
        if (started) {
            throw new RuntimeException("Heartbeat already started");
        }
        heartBeat = new HeartBeatRunner(executor);
        started = true;
    }

    public synchronized void shutdown(){
        if(heartBeat != null){
            heartBeat.running = false;
        }
    }

    public boolean isStarted(){
        return started;
    }

    public boolean isShutdown(){
        return !started;
    }

    /**
     *  standalone daemon thread.
     *  it will start after creation
     */
    private static final class HeartBeatRunner extends Thread {

        private static final int HEARTBEAT_PERIOD_MILLIS = 2000;
        private static final Lock lock = new ReentrantLock();

        final ThreadPoolExecutor threadPoolExecutor;
        volatile boolean running = true;

        HeartBeatRunner(ThreadPoolExecutor threadPoolExecutor){
            this.threadPoolExecutor = threadPoolExecutor;
            super.setName("spy-job-heartbeat-thread-" + Integer.toHexString(hashCode()));
            super.setDaemon(true);
            super.start();
        }

        @Override
        public void run() {

            while(running){
                try {
                    TimeUnit.SECONDS.sleep(HEARTBEAT_PERIOD_MILLIS);
                }catch(Throwable e){
                }

                if(!lock.tryLock()){
                    continue;
                }

                try{
                    // balance load

                    // loop

                }catch(Throwable e){

                }finally{
                    lock.unlock();
                }
            }
        }
    }

}
