package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Date: 2019/6/13 14:14
 * @Description:
 */
public abstract class ShutdownableThread extends Thread{

    private AtomicBoolean isRunning = new AtomicBoolean(true);
    private CountDownLatch shutdown = new CountDownLatch(1);

    public static volatile UncaughtExceptionHandler funcaughtExceptionHandler = null;

    public ShutdownableThread(String name, boolean daemon){
        super(name);
        this.setDaemon(daemon);
        if(funcaughtExceptionHandler !=null ){
            this.setUncaughtExceptionHandler(funcaughtExceptionHandler);
        }
    }

    @Override
    public void run() {
        try{
            execute();
        }catch(Error | Exception e){
            // handle error
        }finally{
            shutdown.countDown();
        }
    }

    public boolean getRunning(){
        return isRunning.get();
    }

    public abstract void execute();

    public void forceShutdown() throws InterruptedException{
        isRunning.set(false);
        interrupt();
    }

    public boolean gracefulShutdown(long time, TimeUnit unit) throws InterruptedException {
        isRunning.set(false);
        return awaitShutdown(time, unit);
    }

    public boolean awaitShutdown(long time, TimeUnit unit) throws InterruptedException {
        return shutdown.await(time, unit);
    }

    public void shutdown(long time, TimeUnit unit) throws InterruptedException {
        if(!gracefulShutdown(time, unit)){
            forceShutdown();
        }
    }


}
