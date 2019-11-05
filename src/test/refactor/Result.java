package refactor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 01378178
 * @Date: 2019/4/28 17:32
 * @Description:
 */
public class Result<T> {

    // 0 成功吗
    private Integer code = 0;
    private String msg = "成功";
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    static String buildQueryCond(){
        QueryCond cond = new QueryCond();
        cond.setWarehouseCode("00124554");
        cond.setCargoCode("SF123213");
        cond.setDate("2019-01-01");
        cond.setItemCateType("1");
        cond.setFirstClassId(1L);
        cond.setSecondClassId(5L);
        cond.setThirdClassId(6L);
        cond.setItemCateType("1");

        return JSON.toJSONString(cond);
    }

    static InvMetricsDTO buildInvMetricJson(){
        return InvMetricsDTO.build();
    }

    static InvTrendEntry buildInvTrendJson(){
        List<String> trends = Arrays.asList(new String[]{
                "12-01",
                "12-02",
                "12-03",
                "12-04",
                "12-05",
                "12-06",
                "12-07",
        });

        List<Integer> qty = Arrays.asList(new Integer[]{
                60,
                45,
                50,
                35,
                77,
                26,
                45,
        });


        return new InvTrendEntry(trends, qty);
    }

    static InvRatioDTO buildInvRatioJson(){
        return InvRatioDTO.build();
    }

    static List<TopSkuEntry> buildTopSkuJson(){
        TopSkuEntry entry = new TopSkuEntry("E0011017", "SM-G9600-64G-勃艮第红", 68 , 68);
        return Lists.newArrayList(entry);
    }

    public static void main(String[] args) {
        System.out.println(buildQueryCond());
        System.out.println(JSON.toJSONString(new Result<>(buildInvMetricJson())));
        System.out.println(JSON.toJSONString(new Result<>(buildInvTrendJson())));
        System.out.println(JSON.toJSONString(new Result<>(buildInvRatioJson())));
        System.out.println(JSON.toJSONString(new Result<>(buildTopSkuJson())));
    }

}
/*class TopSkuDTO{
    List<TopSkuEntry> top10;

    public TopSkuDTO(List<TopSkuEntry> top10) {
        this.top10 = top10;
    }

    public List<TopSkuEntry> getTop10() {
        return top10;
    }

    public void setTop10(List<TopSkuEntry> top10) {
        this.top10 = top10;
    }

    static TopSkuDTO build(){
        TopSkuEntry entry = new TopSkuEntry("E0011017", "SM-G9600-64G-勃艮第红", 68 , 68);
        TopSkuDTO dto = new TopSkuDTO(Lists.newArrayList(entry));
        return dto;
    }
}*/

class TopSkuEntry{
    private String skuCode;
    private String skuName;
    private Integer invQty;
    private Integer availQty;

    public TopSkuEntry(String skuCode, String skuName, Integer invQty, Integer availQty) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.invQty = invQty;
        this.availQty = availQty;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getInvQty() {
        return invQty;
    }

    public void setInvQty(Integer invQty) {
        this.invQty = invQty;
    }

    public Integer getAvailQty() {
        return availQty;
    }

    public void setAvailQty(Integer availQty) {
        this.availQty = availQty;
    }

}

class InvRatioDTO{

    List<IntRationCtnt> warehouseRatio;
    List<IntRationCtnt> cateRatio;
    List<IntRationCtnt> skuRatio;

    public InvRatioDTO(List<IntRationCtnt> warehouseRatio, List<IntRationCtnt> cateRatio, List<IntRationCtnt> skuRatio) {
        this.warehouseRatio = warehouseRatio;
        this.cateRatio = cateRatio;
        this.skuRatio = skuRatio;
    }

    public List<IntRationCtnt> getWarehouseRatio() {
        return warehouseRatio;
    }

    public void setWarehouseRatio(List<IntRationCtnt> warehouseRatio) {
        this.warehouseRatio = warehouseRatio;
    }

    public List<IntRationCtnt> getCateRatio() {
        return cateRatio;
    }

    public void setCateRatio(List<IntRationCtnt> cateRatio) {
        this.cateRatio = cateRatio;
    }

    public List<IntRationCtnt> getSkuRatio() {
        return skuRatio;
    }

    public void setSkuRatio(List<IntRationCtnt> skuRatio) {
        this.skuRatio = skuRatio;
    }

    static InvRatioDTO build(){
        List<IntRationCtnt> warehouseRatio = Arrays.asList(
                new IntRationCtnt("广州仓", 1727, 6),
                new IntRationCtnt("深圳仓", 2879, 10),
                new IntRationCtnt("北京仓", 5758, 20),
                new IntRationCtnt("杭州仓", 10940, 38),
                new IntRationCtnt("上海仓", 7485, 26)
        );

        List<IntRationCtnt> cateRatio = Arrays.asList(
                new IntRationCtnt("类别1", 1727, 6),
                new IntRationCtnt("类别2", 2879, 10),
                new IntRationCtnt("类别3", 5758, 20),
                new IntRationCtnt("类别4", 10940, 38),
                new IntRationCtnt("其他", 7485, 26)
        );

        List<IntRationCtnt> skuRatio = Arrays.asList(
                new IntRationCtnt("SKU1", 1727, 6),
                new IntRationCtnt("SKU2", 2879, 10),
                new IntRationCtnt("SKU3", 5758, 20),
                new IntRationCtnt("SKU4", 10940, 38),
                new IntRationCtnt("其他", 7485, 26)
        );

        InvRatioDTO dto  = new InvRatioDTO(warehouseRatio, cateRatio, skuRatio);

        return dto;
    }
}

class IntRationCtnt{
    private String ctnt;
    private Integer qty;
    private Integer percent;

    public IntRationCtnt(String ctnt, Integer qty, Integer percent) {
        this.ctnt = ctnt;
        this.qty = qty;
        this.percent = percent;
    }

    public String getCtnt() {
        return ctnt;
    }

    public void setCtnt(String ctnt) {
        this.ctnt = ctnt;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}

class InvMetricsDTO{

    private Integer invItemQty;
    private Integer invSkuQty;
    private Integer intransitItemQty;
    private Integer defectItemQty;

    public InvMetricsDTO(Integer invItemQty, Integer invSkuQty, Integer intransitItemQty, Integer defectItemQty) {
        this.invItemQty = invItemQty;
        this.invSkuQty = invSkuQty;
        this.intransitItemQty = intransitItemQty;
        this.defectItemQty = defectItemQty;
    }

    public Integer getInvItemQty() {
        return invItemQty;
    }

    public void setInvItemQty(Integer invItemQty) {
        this.invItemQty = invItemQty;
    }

    public Integer getInvSkuQty() {
        return invSkuQty;
    }

    public void setInvSkuQty(Integer invSkuQty) {
        this.invSkuQty = invSkuQty;
    }

    public Integer getIntransitItemQty() {
        return intransitItemQty;
    }

    public void setIntransitItemQty(Integer intransitItemQty) {
        this.intransitItemQty = intransitItemQty;
    }

    public Integer getDefectItemQty() {
        return defectItemQty;
    }

    public void setDefectItemQty(Integer defectItemQty) {
        this.defectItemQty = defectItemQty;
    }

    static InvMetricsDTO build(){
        InvMetricsDTO dto = new InvMetricsDTO(29270, 398,3806,2315);
        return dto;
    }
}

/*class InvTrendDTO{

    private List<InvTrendEntry> data;

    public InvTrendDTO(List<InvTrendEntry> data) {
        this.data = data;
    }

    public List<InvTrendEntry> getData() {
        return data;
    }

    public void setData(List<InvTrendEntry> data) {
        this.data = data;
    }

    static InvTrendDTO build(){
        List<InvTrendEntry> data = Arrays.asList(new InvTrendEntry[]{
                        new InvTrendEntry("12-01", 60),
                        new InvTrendEntry("12-02", 45),
                        new InvTrendEntry("12-03", 50),
                        new InvTrendEntry("12-04", 35),
                        new InvTrendEntry("12-05", 77),
                        new InvTrendEntry("12-06", 26),
                        new InvTrendEntry("12-07", 45)
                }
        );

        InvTrendDTO t = new InvTrendDTO(data);
        return t;
    }
}*/

class InvTrendEntry{

    private List<String> flexDate;

    private List<Integer> qty;

    public InvTrendEntry(List<String> flexDate, List<Integer> qty) {
        this.flexDate = flexDate;
        this.qty = qty;
    }

    public List<String> getFlexDate() {
        return flexDate;
    }

    public void setFlexDate(List<String> flexDate) {
        this.flexDate = flexDate;
    }

    public List<Integer> getQty() {
        return qty;
    }

    public void setQty(List<Integer> qty) {
        this.qty = qty;
    }
}

class QueryCond{
    private String cargoCode;
    private String warehouseCode;

    // 1 默认, 2 我的分类
    private String itemCateType;


    private Long firstClassId;
    private Long secondClassId;
    private Long thirdClassId;

    // yyyy-MM-dd
    private String date;

    // 1 按日， 2 按月
    private Integer invTrendType;

    public String getCargoCode() {
        return cargoCode;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getItemCateType() {
        return itemCateType;
    }

    public void setItemCateType(String itemCateType) {
        this.itemCateType = itemCateType;
    }

    public Long getFirstClassId() {
        return firstClassId;
    }

    public void setFirstClassId(Long firstClassId) {
        this.firstClassId = firstClassId;
    }

    public Long getSecondClassId() {
        return secondClassId;
    }

    public void setSecondClassId(Long secondClassId) {
        this.secondClassId = secondClassId;
    }

    public Long getThirdClassId() {
        return thirdClassId;
    }

    public void setThirdClassId(Long thirdClassId) {
        this.thirdClassId = thirdClassId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getInvTrendType() {
        return invTrendType;
    }

    public void setInvTrendType(Integer invTrendType) {
        this.invTrendType = invTrendType;
    }
}
