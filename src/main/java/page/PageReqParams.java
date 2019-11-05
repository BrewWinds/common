package page;

import com.google.common.collect.ImmutableSet;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
 * @Date: 2019/2/14 13:58
 * @Description:
 */
public class PageReqParams implements java.io.Serializable {

    private int pageNum = -1;
    private int pageSize = -1;

    private int offset = -1;
    private int limit = -1;

    private String sort = null;

    public static final String SORT_PARAM = "sort";

    public static final Set<String> PAGE_PARAMS = ImmutableSet.of(
            PageHandler.DEFAULT_PAGE_NUM, PageHandler.DEFAULT_PAGE_SIZE,
            PageHandler.DEFAULT_OFFSET, PageHandler.DEFAULT_LIMIT
    );

    private final Map<String, Object> params = new LinkedHashMap();

    public PageReqParams(){}

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
        params.put(PageHandler.DEFAULT_PAGE_NUM, pageNum);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        params.put(PageHandler.DEFAULT_PAGE_SIZE, pageSize);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        params.put(PageHandler.DEFAULT_OFFSET, pageSize);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
        params.put(PageHandler.DEFAULT_LIMIT, pageSize);
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
        params.put(SORT_PARAM, sort);
    }

    public void put(String key, Object val){
        this.params.put(key, val);
    }
}
