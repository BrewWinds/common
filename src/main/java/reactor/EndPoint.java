package reactor;

import org.apache.commons.lang3.StringUtils;

import java.security.Security;

/**
 * @Auther: 01378178
 * @Date: 2019/7/1 10:58
 * @Description:
 */
public class EndPoint {
//    private String uriParseExp = "^(.*)://\[?([0-9a-zA-z\-%._:]*)\]?:(-?[0-9]+)";

    private String host;
    private Integer port;
    private ListenerName listenerName;
    private SecurityProtocol securityProtocal;

    public EndPoint(String host, Integer port, ListenerName listenerName, SecurityProtocol securityProtocol){
        this.host = host;
        this.port = port;
        this.listenerName = listenerName;
        this.securityProtocal = securityProtocol;
    }

    public String connectionString(){
        return StringUtils.isBlank(host) ? (":" + port) : (
                host.contains(":") ? ("[" + host+"]:" + port) : (host + ":" + port));
    }
}
