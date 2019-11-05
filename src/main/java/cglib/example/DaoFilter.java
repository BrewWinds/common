package cglib.example;

import org.springframework.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @Date: 2019/1/8 11:44
 * @Description:
 */
public class DaoFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        if("select".equals(method.getName())){
            return 0;
        }
        return 1;
    }
}
