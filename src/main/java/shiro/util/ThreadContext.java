package main.java.shiro.util;

import main.java.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {

    private static final ThreadLocal<Map<Object, Object>> resources = new InheritableThreadLocal<Map<Object, Object>>();


    private static final String SECURITY_MANAGER_KEY= ThreadContext.class.getName()+"_SECURITY_MANAGER_KEY";
    private static final String SUBJECT_KEY = ThreadContext.class.getName() + "_SUBJECT_KEY";


    public static Map<Object, Object> getResouce(){
        return resources == null ? null : new HashMap<>(resources.get());
    }

    public void setResource(Map<Object, Object> newResources){
        if(newResources==null || newResources.size()==0){
            return ;
        }

        resources.get().clear();
        resources.get().putAll(newResources);
    }


    public static Object get(Object key){

        Object value = getValue(key);
        return value;
    }

    private static Object getValue(Object key){
        return resources.get().get(key);
    }

    public static void put(Object key, Object value){
        if(key==null){
            throw new IllegalArgumentException("key cannot be null");
        }

        if(value==null){
            remove(key);
            return;
        }

        resources.get().put(key, value);
    }

    public static Object remove(Object key){
        Object value = resources.get().get(key);
        return value;
    }

    public static void remove(){
        resources.remove();
    }



    public static SecurityManager getSecurityManager(){
        return (SecurityManager) get(SECURITY_MANAGER_KEY);
    }

    public static void bind(SecurityManager securityManager){
        if(securityManager!=null){
            put(SECURITY_MANAGER_KEY, securityManager);
        }
    }

    public static SecurityManager unbindSecurityManager(){
        return (SecurityManager) remove(SECURITY_MANAGER_KEY);
    }


    public static Subject getSubject(){
        return (Subject) get(SUBJECT_KEY);
    }

    public static void bind(Subject subject){
        if(subject!=null){
            put(SUBJECT_KEY, subject);
        }
    }

    public static Subject unbindSubject(){
        return (Subject) remove(SUBJECT_KEY);
    }


    private static final class InheritableThreadContextMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>>{

        protected Map<Object, Object> initialValue(){
            return new HashMap<>();
        }


        protected Map<Object, Object> childValue(Map<Object, Object> parentValue){
            if(parentValue != null){
                return (Map<Object, Object>) ((HashMap<Object, Object>)parentValue).clone();
            }else{
                return null;
            }
        }

    }

}
