package schedule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistrySupport<T> {

    private static volatile RegistrySupport instance;

    protected Map<String, T> scheduleMap = new ConcurrentHashMap<>();

    public static RegistrySupport getInstance(){
        if(null == instance){
            synchronized (RegistrySupport.class){
                if(null == instance){
                    instance = new RegistrySupport();
                }
            }
        }
        return instance;
    }

    public void addSchedule(final String name, T t){
        scheduleMap.put(name, t);
    }

    public T getSchedule(final String name){
        return scheduleMap.get(name);
    }
}
