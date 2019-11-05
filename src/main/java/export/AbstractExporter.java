package export;


import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @Date: 2018/11/16 16:58
 * @Description:
 */
public abstract class AbstractExporter<T> implements DataExporter<T> {

    public static final int AWAIT_TIME_MILLIS = 31;

    private boolean empty = true;
    private String name;

    @Override
    public boolean isEmpty() {
        return empty;
    }

    public final void nonEmpty(){
        this.empty = false;
    }

    public final AbstractExporter<T> setName(String name){
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    protected final void rollingTbody(Table table, BiConsumer<Object[], Integer> action){
        try{
            Object[] data;
            Function<Object[], Object[]> convert = table.getConvert();
            for(int i=0; !table.isEnd();){
                if((data = table.poll()) != null) {
                    action.accept(convert != null ? convert.apply(data) : data, i++);
                }else{
                    Thread.sleep(AWAIT_TIME_MILLIS);
                }
            }
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

}
