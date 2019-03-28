package nio.reactor.multithread;

import nio.reactor.singlethread.Handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
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
public class Multireactor implements Runnable{

    Selector[] selectors;
    int next = 0;
    final ServerSocketChannel serverSocket;

    public Multireactor(int port) throws IOException {

        // 先注册 OP_ACCEPT 事件
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);

//        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
//        sk.attach(new Acceptor());
    }

    @Override
    public void run() {

    }

    class Acceptor{
        public synchronized void run() throws IOException {
            SocketChannel connection = serverSocket.accept();
            if(connection!=null){
//                new Hanlder(selectors[next], connection);
            }
            if(++next==selectors.length){
                next = 0;
            }
        }
    }
}
