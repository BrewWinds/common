package web;

import util.RegexUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @Description:
 */
public class Networks {

    private Networks(){}

    public static final String LOCALHOST_IP = "127.0.0.1";
    public static final String LOCALHOST_NAME = "locahost";
    public static final String EMPTY_IP = "0.0.0.0";

    private static final long[] MASK = {0xFF000000, 0x00FF0000, 0x0000FF00, 0X000000FF};

    public static final long MAX_IP_VALUE = (1L << 32) - 1;

    public static final String HOST_IP = getHostIp();

    public static String getHostIp(){
        InetAddress address = getHostAddress();
        return address == null ? LOCALHOST_IP :address.getHostAddress();
    }

    public static InetAddress getHostAddress() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if(isValidHostAddress(localAddress)){
                return localAddress;
            }
        } catch (UnknownHostException e) {

        }

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {
                    try {
                        Enumeration<InetAddress> addresses = networkInterfaces.nextElement().getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            try {
                                InetAddress address = addresses.nextElement();
                                if (isValidHostAddress(address)) {
                                    return address;
                                }
                            } catch (Throwable e) {

                            }
                        }
                    } catch (Throwable e) {

                    }
                }
            }
        }catch(Throwable e){

        }

        return localAddress;
    }

    public static boolean isValidHostAddress(InetAddress address){
        if(address == null || address.isLoopbackAddress()){
            return false;
        }

        String ip = address.getHostAddress();
        return ip != null
                && !EMPTY_IP.equals(ip)
                && !LOCALHOST_IP.equals(ip)
                && RegexUtils.isIp(ip);
    }
}
