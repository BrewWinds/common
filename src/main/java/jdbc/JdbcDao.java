package jdbc;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableBiMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: 01378178
 * @Date: 2018/11/15 14:54
 * @Description:
 */
public abstract class JdbcDao<T> {

    protected final Class<T> classType;
    protected final ImmutableBiMap<String, Field> fieldMap;
    protected final String tableName;

    protected JdbcTemplate template;

    public JdbcDao(){

        Class<?> clazz = this.getClass();


        Type type = clazz.getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType)type;
        classType = (Class) pt.getActualTypeArguments()[0];

//        classType = GenericUtils.getActualType(clazz, 0);
        if(!JdbcDao.class.isAssignableFrom(this.classType)){
            throw new UnsupportedOperationException("Generic Type must be JdbcDao");
        }

        try{
            classType.getConstructor();
        }catch(Exception e){
            throw new UnsupportedOperationException("Class "+classType.getSimpleName()+" has not default constructor");
        }

        // 填充 Map
        this.fieldMap = ImmutableBiMap.<String, Field>builder().putAll(
                Arrays.stream(classType.getFields()).collect(
                        Collectors.toMap(x->{
                JdbcField f = x.getAnnotation(JdbcField.class);
                return ( f==null || StringUtils.isBlank(f.column()))?
                        CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, x.getName()) : f.column();
        }, Function.identity()))).build();

        // 非继承的 annotation
        JdbcTable table = classType.getDeclaredAnnotation(JdbcTable.class);
        String tableName = (table!=null && StringUtils.isNotBlank(table.tableName())) ?
                table.tableName() : CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());

        this.tableName = StringUtils.isBlank(table.namespace()) ? tableName : table.namespace()+"."+tableName;
    }


}
