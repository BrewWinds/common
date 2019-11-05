package export;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

/**
 * @Date: 2019/8/19 11:14
 * @Description:
 */
public class Table implements Serializable {

    private static final long serialVersionUID = -3436999745743787412L;

    // 标题
    private String caption;

    //
    private final List<String> thead;
    private final Function<Object[] , Object[]> convert;
    private Object[] tfoot;
    private String comment;
    private Map<CellStyleOption, Object> options;

    private final Queue<Object[]> tbody = new LinkedBlockingQueue<>();
    private volatile boolean empty = true;
    private volatile boolean end = false;

    public Table(List<String> thead, String caption, Object[] tfoot,
                 String comment, Map<CellStyleOption, Object> options,
                 Function<Object[], Object[]> convert){
        this.thead = thead;
        this.caption = caption;
        this.tfoot = tfoot;
        this.comment = comment;
        this.options = options;
        this.convert = convert;
    }


    public Table(List<String> list){
        this(list, null);
    }

    public void addRowsAndEnd(List<Object[]> rows){
        addRows(rows);
        end();
    }

    public Table(List<String> list, Function<Object[], Object[]> convert){
        this.thead = list;
        this.convert = convert;
    }

    public void addRows(List<Object[]> rows){

        if(CollectionUtils.isEmpty(rows)){
            return;
        }
        for(Object[] row : rows){
            tbody.offer(row);
        }
        empty = false;
    }

    public void addRow(Object[] row){
        if(row==null){
            return;
        }
        tbody.offer(row);
        empty = false;
    }

    public synchronized Table end(){
        this.end = true;
        return this;
    }

    public Table copyOfWithoutTbody(){
        return new Table(this.thead,
                this.caption, this.tfoot,
                this.comment, this.options, this.convert);
    }

    boolean isEnd(){
        return end && tbody.isEmpty();
    }

    boolean isEmptyTbody(){
        return empty && isEnd();
    }

    Object[] poll(){
        return tbody.poll();
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<String> getThead() {
        return thead;
    }

    public Function<Object[], Object[]> getConvert() {
        return convert;
    }

    public Object[] getTfoot() {
        return tfoot;
    }

    public void setTfoot(Object[] tfoot) {
        this.tfoot = tfoot;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Map<CellStyleOption, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<CellStyleOption, Object> options) {
        this.options = options;
    }

    public Queue<Object[]> getTbody() {
        return tbody;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
