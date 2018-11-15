package cache;

import com.google.common.base.Preconditions;
import date.TimeProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Cache<T> {

    private static volatile ScheduledExecutorService defaultExecutor;

    private final long ETERNAL = 0;

    private final long aliveTimeInMillis;
    private final boolean isCaseSensitive;
    private final Map<Comparable<?>, CacheValue<T>> cache = new ConcurrentHashMap<>();

    private volatile boolean isDestory = false;

    private ScheduledExecutorService executor;

    private final ReentrantLock lock = new ReentrantLock();
    private TimeProvider now = TimeProvider.now;

    Cache(boolean isCaseSensitive, long aliveTimeInMillis, long autoReleaseInSeconds, ScheduledExecutorService executor){

        this.isCaseSensitive = isCaseSensitive;
        this.aliveTimeInMillis = aliveTimeInMillis;

        if(autoReleaseInSeconds > 0){
            // prototype used
            ScheduledExecutorService executor0;
            if(executor!=null){
                this.executor = executor0 = executor;
            }else{
                if(defaultExecutor == null){
                    synchronized (Cache.class){
                        if(defaultExecutor == null){
                            defaultExecutor = Executors.newScheduledThreadPool(1);
                            Runtime.getRuntime().addShutdownHook(new Thread(defaultExecutor::shutdown));
                        }
                    }
                }
                executor0 = defaultExecutor;
            }

            executor.scheduleAtFixedRate(()->{
                if(!lock.tryLock()){
                    return;
                }
                try{
                    cache.entrySet().removeIf(x->x.getValue().isExpire(now.getTime()));
                }finally{
                    lock.unlock();
                }

            }, autoReleaseInSeconds, autoReleaseInSeconds, TimeUnit.SECONDS);
        }
    }

    public long getAliveTimeInMillis() {
        return aliveTimeInMillis;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void destroy(){
        isDestory = true;
        if(executor!=null){
            try {
                executor.shutdown();
            }catch(Exception e){
                //
            }
        }
        cache.clear();
    }

    public void clean(){
        Preconditions.checkState(!isDestory);
        cache.clear();
    }

    public void set(Comparable<?> key, T value){
        long expireInTimeMillis = 0;
        if(aliveTimeInMillis > 0){
            expireInTimeMillis = now.getTime() + aliveTimeInMillis;
        }else{
            expireInTimeMillis = ETERNAL;
        }
        set(key, value, expireInTimeMillis);
    }

    public void set(Comparable<?> key, T value,  long expireTimeInMillis){

        Preconditions.checkState(!isDestory);

        if(expireTimeInMillis < ETERNAL){
            expireTimeInMillis = ETERNAL;
        }

        if(expireTimeInMillis == ETERNAL || expireTimeInMillis > now.getTime()){
            cache.put(getEffectiveKey(key), new CacheValue<>(value, expireTimeInMillis));
        }

    }

    public T get(Comparable<?> key){
        if(isDestory){
            return null;
        }

        key = getEffectiveKey(key);
        CacheValue<T> val = cache.get(key);
        if(val==null){
            return null;
        }else if(val.isExpire(now.getTime())){
            cache.remove(key);
            return null;
        }

        return val.getVale();
    }

    private Comparable<?> getEffectiveKey(Comparable<?> key){
        if(key instanceof CharSequence){
            if(!isCaseSensitive) {
                key = key.toString().toLowerCase();
            }
        }
        return key;
    }
}
