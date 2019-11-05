package hbase;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Auther: 01378178
 * @Date: 2018/12/4 11:02
 * @Description:
 */
public abstract class HbaseMap<V, R extends Serializable & Comparable<R>>
    extends HashMap<String, V> implements Comparable<HbaseMap<V, R>>{

    public static final String ROW_KEY_NAME = "rowKey";
    public static final String ROW_NUM_NAME = "rowNum";
}
