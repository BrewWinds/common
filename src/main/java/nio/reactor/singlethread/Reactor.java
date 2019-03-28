package nio.reactor.singlethread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * http://gee.cs.oswego.edu/dl/cpjslides/nio.pdf
 * @Description:
 */
public class Reactor implements Runnable{

    final Selector selector;
    final ServerSocketChannel serverSocket;

    public Reactor(int port) throws IOException {

        // 先注册 OP_ACCEPT 事件
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);

        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while(it.hasNext()){
                    dispatch(it.next());
                }
                selected.clear();
            }
        }catch(IOException e){

        }
    }

    public void dispatch(SelectionKey key){
        Runnable r = (Runnable) key.attachment();
        if(r!=null){
            r.run();
        }
    }

    // 接收器
    class Acceptor implements Runnable{

        @Override
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if(c!=null){
                    new Handler(selector, c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
