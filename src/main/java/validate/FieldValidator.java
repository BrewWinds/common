package validate;

import cache.Cache;
import cache.CacheBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.commons.lang3.StringUtils.*;

public class FieldValidator {



    private static final Cache<CacheResult> CFG_META = CacheBuilder.newInstance().build();
    private static final ReentrantLock LOCK = new ReentrantLock();

    public FieldValidator(){
    }


    private static final class CacheResult{

        boolean valid;

        String msg;

        public CacheResult(boolean valid, String msg){
            this.valid = valid;
            this.msg = msg;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


    public void validate(Object bean){

        Class clazz = bean.getClass();
        if(clazz.isInterface()){
            return ;
        }

        while( clazz!= Object.class){
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                Constraint cst = field.getAnnotation(Constraint.class);
                if(cst!=null && !Modifier.isStatic(field.getModifiers())){
                    try {
                        String name = field.getName();
                        Object val = field.get(bean);

                        String error = constraint(clazz.getCanonicalName(), name, val, cst, field.getType());
                    }catch(Exception e){

                    }
                }
            }


            clazz = clazz.getSuperclass();
        }
    }

    public String constraint(String name, String filedName, Object val, Constraint cst, Class<?> type){
        name = name+"@"+filedName;

        StringBuilder builder = new StringBuilder();
        CacheResult result = CFG_META.get(name);
        if(result == null){

            LOCK.lock();
            try {
                if ((result = CFG_META.get(name)) == null) {
                    try {
                        verifyMeta(type, cst);
                        result = new CacheResult(true, "");
                        CFG_META.set(name, result);
                    } catch (Exception e) {
                        result = new CacheResult(false, e.getMessage());
                        CFG_META.set(name, result);
                    }
                }
            }finally{
                LOCK.unlock();
            }
        }

        if(!result.isValid()){
            throw new UnsupportedOperationException("");
        }

        return verifyValue(filedName, val, cst);
    }

    public String verifyValue(String field, Object val, Constraint cst){
        String error = verify(field, val, cst);
        if(isNotBlank(error)){
            return isNotBlank(cst.msg()) ? cst.msg() :  "";
        }else {
            return "";
        }
    }

    public String verify(String field, Object val, Constraint cst){
        if(!cst.notNull() && val==null){
            return "";
        }

        return "";
    }


    public boolean verifyMeta(Class<?> type, Constraint cst){
        return true;
    }


}

