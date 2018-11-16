package validate;

import cache.Cache;
import cache.CacheBuilder;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import reflect.Fields;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.commons.lang3.StringUtils.*;

public class FieldValidator {

    private static final Cache<CacheResult> CFG_META = CacheBuilder.newInstance().build();
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final String EMPTY = "";
    private static final String CFG_ERR = "约束配置错误[";

    protected FieldValidator(){}

    public static FieldValidator newInstance(){
        return new FieldValidator();
    }

    private static final class CacheResult{

        final boolean valid;
        final String msg;

        public CacheResult(boolean valid, String msg){
            this.valid = valid;
            this.msg = msg;
        }

    }

    public void validate(Object bean) {

        Class clazz = bean.getClass();
        if(clazz.isInterface()){
            throw new UnsupportedOperationException("unsupported interface constraint");
        }

        StringBuilder builder = new StringBuilder();
        while( clazz!= Object.class){
            for(Field field : clazz.getDeclaredFields()){
                Constraint cst = field.getAnnotation(Constraint.class);
                if(cst!=null && !Modifier.isStatic(field.getModifiers())){
                    String name = field.getName();
                    Object val = Fields.get(bean, field);
                    String error = constraint(clazz.getCanonicalName(), name, val, cst, field.getType());
                    builder.append(error);
                }
            }
            clazz = clazz.getSuperclass();
        }
        if(builder.length() > 0){
            throw new IllegalArgumentException(builder.toString());
        }
    }

    private String constraint(String name, String filedName, Object val, Constraint cst, Class<?> type){
        name = name+"@"+filedName;
        CacheResult result = CFG_META.get(name);
        if(result == null){

            LOCK.lock();
            try {
                if ((result = CFG_META.get(name)) == null) {
                    try {
                        verifyMeta(filedName, cst, type);
                        result = new CacheResult(true, "");
                        CFG_META.set(name, result);
                    } catch (Exception e) {
                        result = new CacheResult(false, e.getMessage());
                        CFG_META.set(name, result);
                    }
                }
            }finally{
                LOCK.unlock();
            }
        }

        if(!result.valid){
            throw new UnsupportedOperationException("");
        }

        return verifyValue(filedName, val, cst);
    }

    private String verifyValue(String field, Object val, Constraint cst){
        String error = verify(field, val, cst);
        if(isNotBlank(error)){
            return isNotBlank(cst.msg()) ? cst.msg() :  error;
        }else {
            return "";
        }
    }


    private String verify(String f, Object v, Constraint cst){


        // 可以为null且值为null
        if(!cst.notNull() && v==null){
            return EMPTY;
        }

        // 是否不能为blank
        if(cst.notBlank() && isBlank((CharSequence)v)){
            return format(f, v, "不能为空;");
        }

        // 不能为 empty
        if(cst.notEmpty() && isEmpty(v)){
            return format(f, v, "不能为empty;");
        }

        // 不能为 null
        if(cst.notNull() && v == null){
            return  format(f, "null", "不能为null;");
        }

        // 正则表达
        if(!(cst.notNull() && v==null) && isNotBlank(cst.regExp())
            && (v==null || v.toString().matches(cst.regExp()))){
            return  format(f, "null", "格式不匹配"+cst.regExp());
        }

        // 最大字符长度
        if(cst.maxLen() > -1 && v!=null && ((CharSequence)v).length() > cst.maxLen()){
            return  format(f, v, "不能大于"+ cst.maxLen()+"个字符");
        }

        // 最小字符长度
        if(cst.min() > -1 && (v==null || ((CharSequence)v).length() < cst.minLen())){
            return format(f, v, "：不能小于" + cst.minLen() + "个字符;");
        }

        // 整数限制
        if (cst.max() != Long.MAX_VALUE && v != null && Long.parseLong(v.toString()) > cst.max()) {
            return format(f, v, "不能大于" + cst.max() + ";");
        }

        if(cst.min()!=Long.MIN_VALUE && (v!=null || Long.parseLong(v.toString()) < cst.min())){
            return format(f, v, "不能小于"+cst.min()+";");
        }

        // 数列验证
        long[] seqs = cst.series();
        if((seqs!=null && seqs.length > 0) && (v==null ||!ArrayUtils.contains(seqs, Long.parseLong(v.toString())))){
            return format(f, v, "不属于数列"+ Arrays.toString(seqs));
        }

        // 浮点
        if(cst.decimalMax()!=Double.POSITIVE_INFINITY && v!=null
                && Double.parseDouble(v.toString())> cst.decimalMax()){
            return format(f, v, "不能大于"+cst.decimalMax());
        }
        if(cst.decimalMin() != Double.NEGATIVE_INFINITY && v!=null
                && Double.parseDouble(v.toString()) < cst.decimalMin()){
            return format(f, v, "不能小于"+cst.decimalMin());
        }

        // 时间
        Date date = null;
        if(isNotBlank(cst.pattern()) && !(cst.notNull() && (v==null || StringUtils.isBlank(v.toString())))){
            try{
                date = FastDateFormat.getInstance(cst.pattern()).parse((String)v);
            }catch(Exception e){
                return format(f, v, "日期格式不匹配" + cst.pattern()+";");
            }
        }
        // 时态
        if(cst.tense() != Constraint.Tense.ANY){
            if(date == null){
                date = (Date) v;
            }

            String pattern = cst.pattern();
            if(isBlank(pattern)){
                pattern = "yyyy-MM-dd HH:mm:ss";
            }

            if(date == null){
                return format(f, "null", "日期不能为空");
            }else if(cst.tense() == Constraint.Tense.FUTURE && date.before(new Date())){
                return format(f, DateFormatUtils.format(date, pattern), "不能为将来时间");
            }else if(cst.tense() == Constraint.Tense.PAST && date.after(new Date())){
                return format(f, DateFormatUtils.format(date, pattern), "不能为过去时间");
            }
        }
        return EMPTY;
    }

    String format(String f, Object v, String msg){
        String tmp = "{0} [{1}] : {2}";
        return MessageFormat.format(tmp, f, v, msg);
    }


    public boolean verifyMeta(String name, Constraint cst, Class<?> type){

        // 包装类型
        type = ClassUtils.primitiveToWrapper(type);

        // 字符串验证 , 1. 存在正则表达式, 2. 存在maxLen, minLen, 3. 存在日期 datePattern,
        // 4. 非 notBlank, 5. 类型为 CharsetSequence

        if( (isNotBlank(cst.regExp()) || isNotBlank(cst.pattern()) || -1!=cst.maxLen() ||
                 -1!=cst.minLen() || cst.notBlank()) && !CharSequence.class.isAssignableFrom(type)){
            throw new UnsupportedOperationException(CFG_ERR + name + "]：非字符类型不支持字符规则验证");
        }

        // 整数类型验证
        if( !(cst.max() == Long.MAX_VALUE && cst.min() == Long.MIN_VALUE) && !(Long.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type))){
            throw new UnsupportedOperationException(CFG_ERR + name + "]：非整数类型不支持整数数值验证");
        }

        // 数列类型
        if( (cst.series()!=null && cst.series().length > 0) && !(Long.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type)))
        {
            throw new UnsupportedOperationException(CFG_ERR + name + "]：非整数类型不支持数列验证");
        }

        // 小数类型
        if(!(cst.decimalMax()==Double.POSITIVE_INFINITY && cst.decimalMin()==Double.NEGATIVE_INFINITY)&& !(Double.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type))){
            throw new UnsupportedOperationException(CFG_ERR + name + "]：非浮点数类型不支持浮点数值验证");
        }

        // 时间类型
        if( (isBlank(cst.pattern()) && cst.tense()!= Constraint.Tense.ANY) && !Date.class.isAssignableFrom(type)){
            throw new UnsupportedOperationException(CFG_ERR + name + "]：非日期类型不支持时态验证");
        }


        // 字符串, 集合类型
        if(cst.notEmpty() && !emptiable(type)){
            throw new UnsupportedOperationException(CFG_ERR + name + "非集合/字符类型不支持非空验证");
        }

        return true;
    }

    private boolean emptiable(Class<?> type){
        return (CharSequence.class.isAssignableFrom(type) ||
                Collection.class.isAssignableFrom(type)||
                Map.class.isAssignableFrom(type)||
                Dictionary.class.isAssignableFrom(type)||
                type.isArray());
    }

    private boolean isEmpty(Object o){
        if(o==null){
            return true;
        }else if(CharSequence.class.isInstance(o)){
            return ((CharSequence)o).length() == 0;
        }else if(Collection.class.isInstance(o)){
            return ((Collection<?>) o).isEmpty();
        }else if(o.getClass().isArray()){
            return Array.getLength(o) == 0;
        }else if(Map.class.isInstance(o)){
            return ((Map<?,?>) o).isEmpty();
        }else if(Dictionary.class.isInstance(o)){
            return ((Dictionary<?, ?>)o).isEmpty();
        }else {
            return false;
        }
    }


}

