package importer.extractor;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import util.Holder;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Date: 2019/8/20 15:37
 * @Description:
 */
public abstract class DataExtractor<T> {

    protected final Object dataSource;
    protected final String[] headers;

    protected DataExtractor(Object dataSource, String[] headers){
        if(!(dataSource instanceof File) && !(dataSource instanceof InputStream)){
            throw new IllegalArgumentException("datasource either be file or input stream");
        }
        this.dataSource = dataSource;
        this.headers = headers;
    }

    public abstract void extract(RowProcessor<T> processor) throws IOException;

    public void extract(int batchSize, Consumer<List<T>> action,
                        Consumer<T> headerAction) throws IOException {
        Holder<List<T>> holder = Holder.of(Lists.newArrayListWithCapacity(batchSize));
        this.extract((rowNum, data)->{
            if(rowNum == 0){
                headerAction.accept(data);
            }else{
                List<T> list = holder.get();
                list.add(data);
                if(list.size() == batchSize){
                    action.accept(list);
                    holder.set(Lists.newArrayListWithCapacity(batchSize));
                }
            }
        });
        if(CollectionUtils.isNotEmpty(holder.get())){
            action.accept(holder.get());
        }
    }

    public final ValidateResult<T> verify(RowValidator<T> validator) throws IOException {
        ValidateResult<T> rs = new ValidateResult<>();
        this.extract((rowNum, data)->{
            String error = validator.verify(rowNum, data);
            if (StringUtils.isBlank(error)) {
                rs.addData(data);
            } else {
                rs.addError("第" + rowNum + "行错误: " + error);
            }
        });
        return rs;
    }


    protected final InputStream asInputStream(){
        try {
            if(dataSource instanceof File) {
                return new FileInputStream((File) dataSource);}
            else {
                return (InputStream) dataSource;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    public boolean isNotEmpty(T data){
        if(data instanceof String[]){
            return ArrayUtils.isNotEmpty(new Object[]{data});
        }else if(data instanceof String){
            return org.apache.commons.lang.StringUtils.isNotBlank((String) data);
        }
        return data!=null;
    }

}
