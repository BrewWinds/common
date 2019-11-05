package nio.reactor.multithread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date: 2019/3/28 16:57
 * @Description: handler with thread pool
 */
public class PoolHandler implements Runnable{

    SocketChannel socket;
    SelectionKey sk;
    ByteBuffer input = ByteBuffer.allocate(1000);
    ByteBuffer output = ByteBuffer.allocate(5000);

    static final int READING = 0, SENDING = 1, PROCESSING = 3;
    int state = READING;

    static ExecutorService pool = Executors.newFixedThreadPool(10);


    PoolHandler(Selector sel, SocketChannel c) throws IOException {
        socket = c;
        c.configureBlocking(false);
        sk = socket.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    boolean inputIsComplete(){return false;}
    boolean outputIsComplete(){return false;}
    void process(){}

    synchronized void read() throws IOException {
        socket.read(input);
        if(inputIsComplete()){
            state = PROCESSING;
            pool.execute(new Processor());
        }
    }


    /**
     * **************
     */
    synchronized void processAndHandOff(){
        process();
        state = SENDING;
        sk.interestOps(SelectionKey.OP_WRITE);
    }

    // it should not be like this, mostly like a loop event till status complete.
    public void run(){
        try{
            if(state==READING) read();
            else if(state == SENDING) send();
        }catch(IOException e){

        }
    }

    void send() throws IOException{
        socket.write(output);
        if(outputIsComplete()) sk.cancel();
    }

    class Processor implements Runnable{
        public void run(){
            processAndHandOff();
        }
    }
}
