package nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Auther: 01378178
 * @Date: 2019/3/28 14:13
 * @Description:
 */
public final class Handler implements Runnable{

    final SocketChannel socket;
    final SelectionKey sk;
    ByteBuffer input = ByteBuffer.allocate(1000);
    ByteBuffer output = ByteBuffer.allocate(5000);

    static final int READING = 0, SENDING = 1;
    int state = READING;

    Handler(Selector sel, SocketChannel c) throws IOException {
        socket = c;
        c.configureBlocking(false);
        sk = socket.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    @Override
    public void run() {
        try{
            if(state == READING) read();
            else if(state == SENDING) send();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    boolean inputIsComplete(){return false;}
    boolean outputIsComplete(){return false;}
    void process(){}

    void read() throws IOException{
        socket.read(input);
        if(inputIsComplete()){
            process();
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }

    void send() throws IOException{
        socket.write(output);
        if(outputIsComplete()) sk.cancel();
    }
}
