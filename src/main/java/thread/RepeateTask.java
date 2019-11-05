package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2019/1/16 16:10
 * @Description:
 */
 class RepeatTask extends Thread{

    private CountDownLatch latch = new CountDownLatch(1);
    private Logger logger = LoggerFactory.getLogger(RepeatTask.class);
    private volatile boolean stop = false;
    private long sleepTimeMillis;

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTimeMillis);
//                doWork();
            } catch (InterruptedException e) {
                logger.warn("thread "+Thread.currentThread().getId()+" "+Thread.currentThread().getName()+" interrupted.");
            }catch(Throwable th){
                logger.error("thread error", th);
            }
        }
        latch.countDown();
    }

//    abstract void doWork();


    public void shutdown(){

        this.stop = true;
        this.interrupt();

        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("shutdown interrupted", e);
        }

//        logger.info("thread {} {} shutdown completely", this.getName(), this.getId());
        System.out.println("thread "+this.getName()+" " + this.getId()+"shutdown completely");
        System.out.println(Thread.currentThread().getName()+" "+Thread.currentThread().getId());
    }

    public static void main(String[] args) throws InterruptedException {
        RepeatTask a = new RepeatTask();
        a.start();
        Thread.sleep(2000L);
        a.shutdown();
    }
}

