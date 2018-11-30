package query;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 01378178
 * @Date: 2018/11/30 15:25
 * @Description:
 */
public class HbasePageQueryBuilder {
    private final int pageSize;
    private final PageSort pageSort;

    private boolean requireRowNum = true;

    private Object startRow;
    private Boolean inclusiveStartRow;
    private Object stopRow;
    private Boolean inclusiveStopRow;

    private Object rowKeyPrefix;
    private String rowKeyRegexp;

    private Map<String, String[]> famQuas;
    private int maxResultSize = -1;
    private boolean rowKeyOnly = false;


    private HbasePageQueryBuilder(int pageSize, PageSort pageSort){
        Preconditions.checkArgument(pageSize > 0, "pageSize must greater than 0");
        Preconditions.checkArgument(pageSort!=null, "page sort must not be null.");
        this.pageSize = pageSize;
        this.pageSort = pageSort;
    }

    public static HbasePageQueryBuilder newBuilder(int pageSize){
        return new HbasePageQueryBuilder(pageSize, PageSort.ASC);
    }

    public static HbasePageQueryBuilder newBuilder(int pageSize, PageSort pageSort){
        return new HbasePageQueryBuilder(pageSize, pageSort);
    }


    public void setStartRow(Object startRow){
        this.setStartRow(startRow, null);
    }

    public void setStartRow(Object startRow, Boolean inclusiveStartRow){
        this.startRow = startRow;
        this.inclusiveStartRow = inclusiveStartRow;
    }

    public void setStopRow(Object stopRow){
        this.setStopRow(stopRow, null);
    }

    public void setStopRow(Object stopRow, Boolean inclusiveStopRow){
        this.stopRow = stopRow;
        this.inclusiveStopRow = inclusiveStopRow;
    }

    public void setRowKeyRegexp(String rowKeyRegexp){
        this.rowKeyRegexp = rowKeyRegexp;
    }

    public void setRowKeyPrefix(Object rowKeyPrefix){
        this.rowKeyPrefix = rowKeyPrefix;
    }

    public void setFamQuas(Map<String, String[]> famQuas) {
        this.famQuas = famQuas;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageSort getPageSort() {
        return pageSort == null ? PageSort.ASC : pageSort;
    }

    public boolean isRequireRowNum() {
        return requireRowNum;
    }

    public void setRequireRowNum(boolean requireRowNum) {
        this.requireRowNum = requireRowNum;
    }

    public Object getStartRow() {
        return startRow;
    }

    public Boolean isInclusiveStartRow() {
        return inclusiveStartRow != null ? inclusiveStartRow : false;
    }

    public void setInclusiveStartRow(Boolean inclusiveStartRow) {
        this.inclusiveStartRow = inclusiveStartRow;
    }

    public Object getStopRow() {
        return stopRow;
    }

    public Boolean isInclusiveStopRow() {
        return inclusiveStopRow != null ? inclusiveStopRow : false;
    }

    public void setInclusiveStopRow(Boolean inclusiveStopRow) {
        this.inclusiveStopRow = inclusiveStopRow;
    }

    public Object getRowKeyPrefix() {
        return rowKeyPrefix;
    }

    public String getRowKeyRegexp() {
        return rowKeyRegexp;
    }

    public Map<String, String[]> getFamQuas() {
        return famQuas;
    }

    public int getMaxResultSize() {
        return maxResultSize;
    }

    public void setMaxResultSize(int maxResultSize) {
        this.maxResultSize = maxResultSize;
    }

    public boolean isRowKeyOnly() {
        return rowKeyOnly;
    }

    public void setRowKeyOnly(boolean rowKeyOnly) {
        this.rowKeyOnly = rowKeyOnly;
    }

    public <T> T nextPageStartRow(List<T> results){
        return pageStartRow(results, true);
    }

    public <T> T previousPageStartRow(List<T> results){
        return pageStartRow(results, false);
    }

    private <T> T pageStartRow(List<T> results, boolean isNextPage){
        if(CollectionUtils.isEmpty(results)){
            return null;
        }
        return results.get(isNextPage ? results.size() - 1 : 0);
    }

    static enum PageSort{
        ASC, DESC;
    }

    public <T> T orElse(T obj, T defaultValue, Predicate<T> predicate){
        return predicate.test(obj) ? obj : defaultValue;
    }

    static boolean notNull(Object obj){
        return obj != null;
    }
}
