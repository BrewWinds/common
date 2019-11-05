package hbase;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;

/**
 * @Date: 2018/12/4 10:59
 * @Description:
 */
public abstract class HbaseEntity<R extends Serializable & Comparable> implements Serializable, Comparable<HbaseEntity<R>>{

    protected R rowkey;

    protected int rowNum;

    public R getRowkey() {
        return rowkey;
    }

    public void setRowkey(R rowkey) {
        this.rowkey = rowkey;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public int compareTo(HbaseEntity<R> o) {
        if(o==null){
            return 1;
        }

        return new CompareToBuilder().append(rowkey, o.rowkey)
                .toComparison();
    }
}
