package web;


import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

/**
 * @Description:
 */
public class WebUtils {
    private final static Pattern PATTERN_SUFFIX = Pattern.compile("\\S*[?]\\S*");

    public static final String AUTH_HEADER = "X-Auth-Token";
    public static final String AUTH_COOKIE = "auth_token";
    public static final String AUTH_PARAME = "authToken";


    public static String userAgent(HttpServletRequest req){
        return req.getHeader("User-Agent");
    }

    public static String getClientIp(HttpServletRequest req){

        boolean invalid = true;
        String ip = req.getHeader("x-forward-for");

        if(invalid && (invalid = isInvalidIp(ip))){
            ip = req.getHeader("Proxy-Client-IP");
        }
        if(invalid && (invalid = isInvalidIp(ip))){
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if(invalid && (invalid = isInvalidIp(ip))){
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if(invalid && (invalid = isInvalidIp(ip))){
            ip = req.getHeader("X-Real-IP");
        }
        if(invalid && (invalid = isInvalidIp(ip))){
            ip = req.getRemoteAddr();
        }

        if(ip!=null && ip.indexOf(",") > 0){
            ip = ip.substring(0, ip.indexOf(","));
        }

        if("127.0.0.1".equalsIgnoreCase(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)){
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

    public static boolean isAjax(HttpServletRequest req){
        return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
    }

    public static void respJson(HttpServletResponse resp, Object data){
        response(resp, "application/json", JSON.toJSONString(data), "UTF-8");
    }


    public static void response(HttpServletResponse resp, String contentType, String text, String charset){
        resp.setContentType(contentType+";charset="+charset);
        resp.setCharacterEncoding(charset);
        try(PrintWriter writer = resp.getWriter()){
            writer.write(text);
        } catch (IOException e) {
            throw new RuntimeException("response "+contentType +" occur error", e);
        }
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

    public static void cros(HttpServletRequest req, HttpServletResponse resp){

        String origin = req.getHeader("Origin");
        origin = StringUtils.isEmpty(origin) ? "*" : origin;

        resp.setHeader("Access-Control-Allow-Origin", origin);

        String headers = req.getHeader("Access-Control-Allow-Headers");
        headers = StringUtils.isEmpty(headers)
                ? "Origin,No-Cache,X-Requested-With,If-Modified-Since,Pragma," +
                "Expires,Last-Modified,Cache-Control,Content-Type,X-E4M-With" : headers;

        resp.setHeader("Access-Control-Allow-Headers", headers);
        resp.setHeader("Access-Control-Max-Age" , "0");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("XDomainRequestAllowed", "1");

    }

    public static String xssReplace(String text){
        return StringUtils.replaceEach(text,
                new String[]{"<", ">", "%3c", "%3e"},
                new String[]{"&lt;", "&gt;", "&lt;", "&gt;"});
    }

    public static void response(HttpServletResponse resp, InputStream input,
                                String filename, String charset, boolean isGzip){
        try(InputStream in = input;
            OutputStream out = resp.getOutputStream()){

            filename = URLEncoder.encode(filename, charset);

            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Length", Long.toString(in.available()));
            resp.setHeader("Content-Disposition", "form-data; name=\"attachment\";filename=\""+filename+"\"");
            resp.setCharacterEncoding(charset);

            if(isGzip){
                resp.setHeader("Content-Encoding", "gzip");
                // Deflat.setLevel(-1)
                try(GZIPOutputStream gzout = new GZIPOutputStream(out)){
                    IOUtils.copyLarge(in, out);
                    gzout.flush();
                    gzout.finish();
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            }else{
                IOUtils.copyLarge(in, out);
            }

        }catch(IOException e){
            throw new RuntimeException("response byte array data occur error", e);
        }
    }


    /**
     * 设置响应头
     * @param response
     * @param name
     * @param value
     */
    public static void addHeader(HttpServletResponse response, String name, String value){
        response.addHeader(name, value);
    }

    public static String getHeader(HttpServletRequest request, String name){
        return request.getHeader(name);
    }

    /**
     * 回话跟踪
     */
    public static void setSessionTrace(HttpServletResponse response, String token){
        int maxAge = (token == null) ? 0 : 86400;
        addCookie(response, AUTH_COOKIE, token, "/", maxAge);
        addHeader(response, AUTH_HEADER, token);
    }

    /**
     * 回话跟踪
     */
    public static String getSessionTrace(HttpServletRequest request){
        String authToken = request.getParameter(AUTH_PARAME);
        if(authToken != null){
            return authToken;
        }

        authToken = WebUtils.getCookie(request, AUTH_COOKIE);
        if(authToken != null){
            return authToken;
        }

        return getHeader(request, AUTH_HEADER);
    }

}
