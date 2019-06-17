package web;


import org.apache.commons.lang.StringUtils;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.regex.Pattern;

/**
 * @Description:
 */
public class WebUtils {
    private final static Pattern PATTERN_SUFFIX =
            Pattern.compile("\\S*[?]\\S*");

    public static final String AUTH_HEATHER = "X-Auth_Token";
    public static final String AUTH_COOKIE = "auth_token";
    public static final String AUTH_PARAME = "authToken";


    public static String userAgent(HttpServletRequest req){
        return req.getHeader("User-Agent");
    }

    public static String getClientIp(HttpServletRequest req){
        boolean invalid = true;
        String ip = req.getHeader("x-forward-for");
        if(invalid && (invalid == isInvalidIp(ip))){
            ip = req.getHeader("Proxy-Client-IP");
        }
        if(invalid && (invalid == isInvalidIp(ip))){
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if(invalid && (invalid == isInvalidIp(ip))){
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if(invalid && (invalid == isInvalidIp(ip))){
            ip = req.getHeader("X-Real-IP");
        }
        if(invalid && (invalid == isInvalidIp(ip))){
            ip = req.getRemoteAddr();
        }

        if(ip!=null && ip.indexOf(",") > 0){
            ip = ip.substring(0, ip.indexOf(","));
        }
        if("127.0.0.1".equalsIgnoreCase(ip)
                || "0:0:0:0:0:0:0:1".equals(ip)
                ||  "::1".equals(ip)){
            ip = Networks.HOST_IP;
        }
        return ip;
    }


    private static boolean isInvalidIp(String ip){
        return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    }


    public static String getContextPath(HttpServletRequest req){
        return StringUtils.isBlank(req.getContextPath()) ? "/" : req.getContextPath() ;
    }


    public static String getContextPath(ServletContext context){
        String ctxPath  = context.getContextPath();
        return StringUtils.isBlank(ctxPath) ? "/" : ctxPath;
    }


    public static String getCookie(HttpServletRequest req, String name){
        Cookie[] cookies = req.getCookies();
        if(cookies == null){
            return null;
        }

        for(Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void delCookie(HttpServletRequest req,
                                 HttpServletResponse resp, String name){

        Cookie[] cookies = req.getCookies();
        if(cookies == null){
            return;
        }

        for(Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                cookie.setMaxAge(0);
                cookie.setValue(null);
                resp.addCookie(cookie);
                return;
            }
        }
    }

    public static void addCookie(HttpServletResponse resp, String name,
                                 String value, String path, int maxAge){
        resp.addCookie(createCookie(name, value, path, maxAge));
    }

    public static Cookie createCookie(String name, String value,
                                      String path, int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public static Cookie createCookie(String name, String value,
                                      String path, String domain, int maxAge){
        Cookie cookie = createCookie(name, value, path, maxAge);
        cookie.setDomain(domain);
        return cookie;
    }


}
