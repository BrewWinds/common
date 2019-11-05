/*
package hbase;

*/
/**
 * @Date: 2018/12/3 10:36
 * @Description:
 *//*


import com.google.common.collect.ImmutableBiMap;
import hbase.page.PageQueryBuilder;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.io.serializer.Serialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import util.Bytes;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class HbaseDao<T, R extends Serializable & Comparable<R>> {

    private static Logger logger = LoggerFactory.getLogger(HbaseDao.class);

    protected HbaseTemplate template;

    protected final Class<T> classType;
    protected final Class<R> rowKeyType;
    protected final ImmutableBiMap<String, Field> fieldMap;
    protected final String tableName;
    protected final String globalFamily;
    protected final List<byte[]> definedFamilies;
    protected final Class<T> classType;

    @Resource
    protected HbaseTemplate template

    public final Configuration getConfig() {
        return template.getConfiguration();
    }

    public final Connection getConnection() throws IOException {
        return ConnectionFactory.createConnection();
    }

    public final Table getTable(String tableName) throws IOException {
        return getConnection().getTable(TableName.valueOf(tableName));
    }

    private List<T> find(Object startRow, Object endRow, int pageSize, boolean reversed,
                         ScanHook scanHook, boolean inclusiveStartRow) {

        Scan scan = buildScan(startRow, endRow, pageSize, reversed, scanHook);
        List<T> result = template.find(template, scan, )

        return null;
    }

    public List<T> nextPage(PageQueryBuilder query) {
        return page(query, true, query.getPageSort() != PageQueryBuilder.PageSort.ASC);
    }

    public List<T> previousPage(PageQueryBuilder query) {
        return page(query, false, query.getPageSort() == PageQueryBuilder.PageSort.ASC);
    }

    private void setRowKey(HbaseEntity<R> entity, byte[] rowKey){
        entity.setRowkey;
    }

    private <V> void setRowKey(HbaseMap<V, R> map, byte[] rowKey){

    }

    private R convertRowKey(byte[] rowKey){
        R key;
        if(rowKey == null){
            key = null;
        }else if(this.rowKeyType.equals(byte[].class)){
            key = (R)((Object)rowKey);
        }else if(this.rowKeyType.equals(String.class)){
            key = (R) org.apache.hadoop.hbase.util.Bytes.toString(rowKey);
        }else if(this.rowKeyType.equals(ByteArrayWrapper.class)){
            key = (R) new ByteArrayWrapper(rowKey);
        }else{
            try{
                key = this.rowKeyType.getConstructor(byte[].class).newInstance(rowKey);
            }catch(Exception e){

            }
        }
        return key;
    }

    private List<T> page(PageQueryBuilder query, boolean isNextPage, boolean reversed) {

        List<T> result = find(query.getStartRow(), query.getStopRow(), query.getActualPageSize(),
                reversed, pageScanHook(query), query.isInclusiveStartRow());

        if (CollectionUtils.isNotEmpty(result)) {
            if (result.size() > query.getPageSize()) {
                result = result.subList(0, query.getPageSize());
            }

            if (!isNextPage) {
                Collections.reverse(result);
            }

            if (query.isRequireRowNum()) {
                for (int i = 0, n = result.size(); i < n; i++) {
                    setRowNum(result.get(i), i);
                }
            }
        }
        return result;
    }

    private static <T, R extends Serializable & Comparable<R>> void setRowNum(T t, int rowNum) {
        if (t instanceof HbaseEntity) {
            ((HbaseEntity<R>) t).setRowNum(rowNum);
        } else {
            ((Map<String, Object>) t).put(HbaseMap.ROW_NUM_NAME, rowNum);
        }
    }


    protected Scan buildScan(Object startRow, Object stopRow, int pageSize, boolean
            reversed, ScanHook scanHook) {

        Scan scan = new Scan();
        scan.setReversed(reversed);

        byte[] startRowBytes = Bytes.toBytes(startRow);
        if (ArrayUtils.isNotEmpty(startRowBytes)) {
            scan.setStartRow(startRowBytes);
        }

        scanHook.hook(scan);

        Filter filter = scan.getFilter();
        byte[] stopRowBytes = Bytes.toBytes(stopRow);
        if (ArrayUtils.isNotEmpty(stopRowBytes) && !containsFilter(InclusiveStopFilter.class, filter)) {
            scan.setStopRow(stopRowBytes);
        }

        if (pageSize > 0) {
            if (filter == null) {
                filter = new PageFilter(pageSize);
            } else {
                if (!(filter instanceof FilterList)) {
                    filter = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter);
                }

                ((FilterList) filter).addFilter(new PageFilter(pageSize));
            }
        }
        scan.setFilter(filter);
        return scan;
    }

    private RowMapper<T> rowMapper(){
        return (rs, rowNum) -> {
            if(rs.isEmpty()){
                return null;
            }
            Object model = classType.newInstance();
            if(HbaseEntity.class.isAssignableFrom(classType)){
                HbaseEntity<R> entity = (HbaseEntity<R>) model;
            }else if(HbaseMap.class.isAssignableFrom(classType)){

            }else{
                throw new UnsupportedOperationException("Unsupported type: "classType.getCanonicalName());
            }
        };
    }


    private static <E extends Filter> boolean containsFilter(Class<E> type, Filter filter) {
        if (filter == null) {
            return false;
        } else if (!(filter instanceof FilterList)) {
            return type.isInstance(filter);
        } else {
            return ((FilterList) filter).getFilters().stream().anyMatch(f -> type.isInstance(f));
        }
    }


    @FunctionalInterface
    private static interface ScanHook {
        void hook(Scan scan);
    }


    protected ScanHook pageScanHook(PageQueryBuilder query) {
        return scan -> {
            FilterList filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
            byte[] rowKeyPrefixBytes = Bytes.toBytes(query.getRowKeyPrefix());

            if (ArrayUtils.isNotEmpty(rowKeyPrefixBytes)) {
                filters.addFilter(new PrefixFilter(rowKeyPrefixBytes));
            }

            if (StringUtils.isNotEmpty(query.getRowKeyRegexp())) {
                RegexStringComparator regex = new RegexStringComparator(query.getRowKeyRegexp());
                filters.addFilter(new RowFilter(CompareFilter.CompareOp.EQUAL, regex));
            }

            byte[] stopRowBytes = Bytes.toBytes(query.getStopRow());
            if (ArrayUtils.isNotEmpty(stopRowBytes) && query.isInclusiveStopRow()) {
                filters.addFilter(new InclusiveStopFilter(stopRowBytes));
            }

            if (query.isRowKeyOnly()) {
                filters.addFilter(new FirstKeyOnlyFilter());
            } else if (MapUtils.isNotEmpty(query.getFamQuas())) {
                query.getFamQuas().entrySet().forEach(e -> {
                    byte[] family = Bytes.toBytes(e.getKey());
                    String[] qualifies = e.getValue();
                    if (ArrayUtils.isEmpty(qualifies)) {
                        scan.addFamily(family);
                    } else {
                        Stream.of(qualifies).forEach(q -> scan.addColumn(family, Bytes.toBytes(e)));
                    }
                });
            } else {
                addDefinedFamilies(scan);
            }

            scan.setFilter(filters);

            if (query.getMaxResultSize() > -1) {
                scan.setMaxResultSize(query.getMaxResultSize());
            }
        };
    }

    private void addDefinedFamilies(Scan scan) {
        definedFamilies.stream().forEach(family -> scan.addFamily(family));
    }
}


*/
