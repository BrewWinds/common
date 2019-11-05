package nio.reactor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Date: 2019/3/28 16:36
 * @Description:
 */
public class ClassicServerSocket implements Runnable{

    int port;

    @Override
    public void run() {
        try(ServerSocket ss = new ServerSocket(port)){
            while(!Thread.interrupted()){
                new Thread(new Handler(ss.accept())).start();;
            }
        }catch(IOException e){
        }
    }

    static class Handler implements Runnable{

        final Socket socket;
        Handler(Socket s){
            socket = s;
        }

        @Override
        public void run() {
            try{
                byte[] input = new byte[1000];
                socket.getInputStream().read(input);    // block
                byte[] output = process(input);
                socket.getOutputStream().write(output); // block
            }catch(IOException e){
                // logger
            }
        }

        private byte[] process(byte[] cmd){
            return null;
        }
    }


}
