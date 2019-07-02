package reactor;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Auther: 01378178
 * @Date: 2019/7/1 13:50
 * @Description:
 */
public abstract class AbstractServerThread implements Runnable{

    private SocketServer.ConnectionQuotas connectionQuotas;

    public AbstractServerThread(SocketServer.ConnectionQuotas connectionQuotas){
        this.connectionQuotas = connectionQuotas;
    }

    private CountDownLatch startupLatch = new CountDownLatch(1);
    private CountDownLatch shutdownLatch = new CountDownLatch(1);
    private AtomicBoolean alive = new AtomicBoolean(true);

    abstract void wakeup();

    void shutdown() throws InterruptedException {
        if(alive.getAndSet(false)){
            wakeup();
        }
        shutdownLatch.await();
    }

    void awaitStartup() throws InterruptedException {
        startupLatch.await();
    }

    void close(SocketChannel channel){
        if(channel != null){
            connectionQuotas.dec(channel.socket().getInetAddress());
            try {
                channel.socket().close();
                channel.close();
            } catch (IOException e) {
                //TODO swallow exception
            }
        }
    }

    protected void startupComplete(){
        shutdownLatch = new CountDownLatch(1);
        startupLatch.countDown();;
    }


}
