package timewheel;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Date: 2019/6/12 16:10
 * @Description:
 */
public class Pool<K, V> implements Iterable{

    ConcurrentMap<K, V> pool = new ConcurrentHashMap();

    public V put(K k, V v){
        return pool.put(k, v);
    }

    public V putIfNotExists(K k, V v){
        return pool.putIfAbsent(k, v);
    }

    public V get(K k){
        return pool.get(k);
    }

    public V remove(K k){
        return pool.remove(k);
    }

    public Iterator<V> values(){
        return pool.values().iterator();
    }


    @Override
    public Iterator iterator() {

        Iterator ite = new Iterator() {

            private Iterator<Map.Entry<K, V>> iIte = pool.entrySet().iterator();

            @Override
            public boolean hasNext() {
                return iIte.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                Map.Entry<K, V> entry = iIte.next();
                return entry;
            }
        };

        return ite;
    }
}
