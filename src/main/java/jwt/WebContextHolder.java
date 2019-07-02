package jwt;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 01378178
 * @Date: 2019/7/2 18:01
 * @Description:
 */
public class WebContextHolder {

    private static final String CURRENT_USER = "current_user";

    public static User currentUser(){
        return (User) getRequest().getAttribute(CURRENT_USER);
    }

    public static void setUser(User user){
        getRequest().setAttribute(CURRENT_USER, user);
    }

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }



}
