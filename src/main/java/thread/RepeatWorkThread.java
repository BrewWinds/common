package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2019/2/20 14:06
 * @Description:
 */
public abstract class RepeatWorkThread extends Thread{

    private long intervalMillis;
    private long DEFAULT_INTERVAL_MS = 1000;
    private CountDownLatch latch = new CountDownLatch(1);
    private volatile boolean stop = false;

    public RepeatWorkThread(){
        this.intervalMillis = DEFAULT_INTERVAL_MS;
    }

    public RepeatWorkThread(long intervalMillis){
        this.intervalMillis = intervalMillis;
    }

    @Override
    public void run() {
        while(!stop){
            try {
                repeatWork();
                TimeUnit.MILLISECONDS.sleep(intervalMillis);
            } catch (InterruptedException e) {
                //TO LOG
            }
        }

        latch.countDown();
    }


    protected abstract void repeatWork() throws InterruptedException;


    public void shutdown(){
        // to check
        if(!stop){
            this.interrupt();
            stop = true;

            try {
                this.latch.await();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

}
