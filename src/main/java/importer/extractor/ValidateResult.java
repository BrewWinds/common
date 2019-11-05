package importer.extractor;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Date: 2019/8/20 15:50
 * @Description:
 */
public class ValidateResult<T> {

    private final List<T> data = Lists.newArrayList();

    private final List<String> erros = Lists.newArrayList();

    public List<T> getData(){
        return data;
    }

    public void addData(T obj){
        this.data.add(obj);
    }

    public List<String> getErrors(){
        return erros;
    }

    public void addError(String error){
        this.erros.add(error);
    }
}
