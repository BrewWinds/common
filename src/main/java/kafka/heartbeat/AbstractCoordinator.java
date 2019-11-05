package kafka.heartbeat;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Date: 2019/4/22 10:36
 * @Description:
 */
public class AbstractCoordinator {
    private HeartbeatThread heartbeatThread = null;
    private Heartbeat heartbeat = null;
    private long retryBackoffMs = 0;

    private class HeartbeatThread extends Thread implements AutoCloseable{

        private boolean enabled = false;
        private boolean closed = false;
        private AtomicReference<RuntimeException> failed = new
                AtomicReference<RuntimeException>();

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (AbstractCoordinator.this) {
                        if (closed) {
                            return;
                        }

                        if (!enabled) {
                            AbstractCoordinator.this.wait();
                            continue;
                        }


                        long now = System.currentTimeMillis();


//                        if(heartbeat.pollTimeoutExpired(now)){
//                            maybeLeaveGroup();
//                        }else if (!heartbeat.shouldHeartbeat(now)){
//                            AbstractCoordinator.this.wait(retryBackoffMs);
//                        }else{
//                            heartbeat.sentHeartbeat(now);
//                            sendHeartbeatRequest.addListener();
//                        }

                    }
                }
            }catch(Exception e){

            }
        }

        @Override
        public void close() throws Exception {

        }
    }

    public synchronized void maybeLeaveGroup(){
        // build leave group request
        // client.send()
        // clinet.pollNoWakeup();

        resetGeneration();
    }


    public synchronized void resetGeneration(){
    }
}
