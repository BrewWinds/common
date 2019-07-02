package reactor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 01378178
 * @Date: 2019/6/28 17:41
 * @Description:
 */
public class SocketServer {

    private Config config;

    private ConcurrentHashMap<Integer, Processor> dataPlaneProcessors = new ConcurrentHashMap<>();


    class Acceptor{
        public Acceptor(EndPoint endPoint, Integer sendBufferSize, Integer recvBufferSize,
                        Integer brokerId, ConnectionQuotas connectionQuotas, String metricPrefix){

        }
    }

    class ConnectionQuotas{

        private Integer defaultMax;
        private Map<String, Integer> overrideQuotas;
        private Map<InetAddress, Integer> counts = new HashMap();

        private Integer defaultMaxConnPerIp = defaultMax;
        private Map<String, Integer> maxConnectionsPerIpOverrides;

        public ConnectionQuotas(Integer defaultMax, Map<String, Integer> overrideQuotas){
            this.defaultMax = defaultMax;
            this.overrideQuotas = overrideQuotas;
            maxConn();
        }

        public void maxConn(){
            synchronized (counts) {
                maxConnectionsPerIpOverrides.entrySet().forEach(x -> {
                    try {
                        counts.put(InetAddress.getByName(x.getKey()), x.getValue());
                    } catch (UnknownHostException e) {
                        // TODO handle exception
                    }
                });
            }
        }

        public void inc(InetAddress address){
            synchronized (counts){
                Integer count = counts.getOrDefault(address, 0);
                counts.put(address, count+1);
                Integer max = maxConnectionsPerIpOverrides.getOrDefault(address, defaultMaxConnPerIp);
                if(count >= max){
                    throw new RuntimeException("Too many connection="+address.toString()+", max="+max);
                }
            }
        }

        public void dec(InetAddress address){
            synchronized (counts){
                Integer count = counts.get(address);
                if(count==null){
                    throw new IllegalArgumentException("Attempted to decrease connection count for address with no connections, address:"+ address.toString());
                }
                if(count == 1){
                    counts.remove(address);
                }else
                    counts.put(address, count-1);
            }
        }

        public Integer get(InetAddress address){
            return counts.getOrDefault(address, 0);
        }
    }
}
