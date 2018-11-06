package cache;

import java.io.Serializable;

public class CacheValue<T> implements Serializable {

    private long expireTimeInMillis;

    private T value;

    CacheValue(T value, long expireTimeInMillis){
        this.value = value;
        this.expireTimeInMillis = expireTimeInMillis;
    }

    public boolean isExpire(long refTimeInMillis){
        return expireTimeInMillis > refTimeInMillis;
    }

    public T getVale(){
        return this.value;
    }
}
