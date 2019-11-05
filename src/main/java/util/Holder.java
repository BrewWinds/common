package util;

/**
 * @Date: 2018/11/16 17:00
 * @Description:
 */
public class Holder<T> {

    private T val;

    private Holder(T val){
        this.val = val;
    }

    public T get() {
        return val;
    }

    public T set(T val){
        this.val = val;
        return val;
    }

    public Holder val(T val) {
        this.val = val;
        return this;
    }

    public static <T> Holder<T> of(T t){
        return new Holder<>(t);
    }

}
