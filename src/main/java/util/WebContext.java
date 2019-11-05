package util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: 01378178
 * @Date: 2019/9/10 20:46
 * @Description:
 */
public class WebContext {

    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();

    public static HttpServletRequest getRequest(){
        return request.get();
    }

    public static HttpServletResponse getResponse(){
        return response.get();
    }

    private void setRequeset(HttpServletRequest req){
        request.set(req);
    }

    private static void setResponse(HttpServletResponse resp) {
        response.set(resp);
    }

    private static void removeRequest() {
        request.remove();
    }

    private static void removeResponse() {
        response.remove();
    }

    //@WebFilter(filterName = "util.WebContext$WebContextFilter", urlPatterns = "/*")
    public static class WebContextFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            try {
                WebContext.setResponse((HttpServletResponse) request);
                WebContext.setResponse((HttpServletResponse) response);
                chain.doFilter(request, response);
            }finally {
                WebContext.removeRequest();
                WebContext.removeResponse();
            }
        }

        @Override
        public void destroy() {
        }
    }

}
