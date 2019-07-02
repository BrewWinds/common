package reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduler.CustScheduler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Auther: 01378178
 * @Date: 2019/6/28 17:36
 * @Description:
 */
public class KServer {

    Logger logger = LoggerFactory.getLogger(KServer.class);

    private AtomicBoolean startupComplete = new AtomicBoolean(false);
    private AtomicBoolean isShuttingDown = new AtomicBoolean(false);
    private AtomicBoolean isStartingUp = new AtomicBoolean(false);

    private CountDownLatch shutdownLatch = new CountDownLatch(1);


    BrokerState brokerState = new BrokerState();

    CustScheduler custScheduler;
    SocketServer socketServer;

    public void startup(){
        logger.info("kServer starting");

        if(isShuttingDown.get()){
            throw new IllegalStateException("Kserver is still shutting down, cannot re-start");
        }

        if(startupComplete.get()){
            return;
        }

        boolean canStartup = isStartingUp.compareAndSet(false, true);
        if(canStartup){
            brokerState.newState((byte) 1);
        }
    }
}
