package timewheel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Date: 2019/6/12 15:05
 * @Description:
 */
public abstract class ShutdownableThread extends Thread{

    private AtomicBoolean isRunning = new AtomicBoolean(true);
    private CountDownLatch shutdownLatch = new CountDownLatch(1);

    public volatile static UncaughtExceptionHandler funcaughtExceptionHandler = null;

    public ShutdownableThread(String name){
        this(name, true);
    }

    public ShutdownableThread(String name, boolean daemon){
        super(name);
        this.setDaemon(daemon);
        if(funcaughtExceptionHandler != null){
            this.setUncaughtExceptionHandler(funcaughtExceptionHandler);
        }
    }

    public abstract void execute();

    public boolean getRunning(){
        return isRunning.get();
    }

    public void run(){
        try{
            execute();
        }catch(Error | RuntimeException e){
            throw e;
        }finally{
            shutdownLatch.countDown();
        }
    }

    public void shutdown(long gracefulTimeout, TimeUnit unit) throws InterruptedException{
        boolean success = gracefulShutdown(gracefulTimeout, unit);
        if(!success){
            forceShutdown();
        }
    }

    public boolean gracefulShutdown(long timeout, TimeUnit unit)
        throws InterruptedException{
        startGracefulShutdown();
        return awaitShutdown(timeout, unit);
    }

    public void startGracefulShutdown(){
        isRunning.set(false);
    }

    public boolean awaitShutdown(long timeout, TimeUnit unit)
        throws InterruptedException{
        return shutdownLatch.await(timeout, unit);
    }

    public void forceShutdown() throws InterruptedException{
        isRunning.set(false);
        interrupted();
    }
}
