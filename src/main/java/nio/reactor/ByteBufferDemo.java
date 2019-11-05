package nio.reactor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Date: 2019/8/15 15:09
 * @Description:
 */
public class ByteBufferDemo {


    public static void main(String[] args) throws IOException {
        fileChannelDemo();
    }


    public static void fileChannelDemo() throws IOException {

        RandomAccessFile file = new RandomAccessFile("E:\\2018-06-27", "rw");
        FileChannel channel = file.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48); // 分配48个字节
        int bytesRead = channel.read(buf);

        while(bytesRead!=-1){

            buf.flip(); // make buffer ready for read

            while(buf.hasRemaining()){
                System.out.println((char) buf.get());  // read 1 byte at a time
            }

            buf.clear();
            bytesRead = channel.read(buf);
        }
        channel.close();
    }

}
