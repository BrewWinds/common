package kafka;

import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.client.PerClientRandomNonceGenerator;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.*;

/**
 * @Date: 2019/1/14 19:59
 * @Description:
 */
public class ClientTest {

    static List<String> nodes = Lists.newArrayList();

    public static void main(String[] args) {

        Random random = new Random();
        int offset = random.nextInt();
        for(int i=0; i<10; i++){
            nodes.add(i+"");
        }

        int i = 0;


        for(int y=0;y<100; y++) {
            int idx = (Math.abs(offset + y)) % nodes.size();
            System.out.println(idx);
        }
    }
}
