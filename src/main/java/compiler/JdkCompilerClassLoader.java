package compiler;

import javax.tools.JavaFileObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2019/1/8 14:15
 * @Description:
 */
public final class JdkCompilerClassLoader extends ClassLoader{

    private final Map<String, JavaFileObject> classes = new HashMap();

    @Override
    protected synchronized Class<?> findClass(String name) throws ClassNotFoundException {
        JavaFileObject file = classes.get(name);
        if(file != null){
        }
        return super.findClass(name);
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    public static void main(String[] args) {
        System.out.println(JdkCompilerClassLoader.class.getClassLoader());
        System.out.println(JdkCompilerClassLoader.class.getClassLoader().getParent());
        System.out.println(JdkCompilerClassLoader.class.getCanonicalName());
    }
}
