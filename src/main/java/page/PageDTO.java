package page;

/**
 */
public class PageDTO {

    public final int DEF_PAGESIZE  = 10;

    public final int PAGE_START = 1;

    /*表示不分页*/
    public static final String NOPAGING = "0";

    /*默认分页*/
    public static final String DEF_PAGING = "1";
    /**
     * 1表示分页,0表示不分页
     */
    private String paging = DEF_PAGING;

    private Integer currentPage = PAGE_START;


    private Integer pageSize = DEF_PAGESIZE;

    public PageDTO() {
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize <= 0 ? DEF_PAGESIZE : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentIndex() {
        return getCurrentPage() == null || getPageSize() == null
                || getCurrentPage() <= 0 ? 0 : (getCurrentPage() - 1)
                * getPageSize();
    }

    public String getPaging() {
        return paging;
    }

    public void setPaging(String paging) {
        this.paging = paging;
    }
}
