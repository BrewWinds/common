package cglib.example;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Date: 2019/1/8 11:43
 * @Description:
 */
public class DaoAnotherProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("New Proxy Before");
        method.invoke(o, objects);
        System.out.println("New Proxy After");
        return o;
    }
}
