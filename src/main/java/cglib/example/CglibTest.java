package cglib.example;

import org.junit.Test;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

/**
 * @Date: 2019/1/8 11:39
 * @Description:
 */
public class CglibTest {

    public void testCglib(){
        DaoProxy proxy = new DaoProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallback(proxy);

        Dao dao = (Dao) enhancer.create();
        dao.update();
        dao.select();
    }

    public void testCglibWithFilter(){

        DaoProxy proxy = new DaoProxy();
        DaoAnotherProxy daoAnotherProxy = new DaoAnotherProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallbacks(new Callback[]{proxy, daoAnotherProxy, NoOp.INSTANCE});
        enhancer.setCallbackFilter(new DaoFilter());

        Dao dao = (Dao) enhancer.create();
        dao.update();
        dao.select();

    }

    public static void main(String[] args) {
        CglibTest test = new CglibTest();
//        test.testCglib();
        test.testCglibWithFilter();
    }

}
