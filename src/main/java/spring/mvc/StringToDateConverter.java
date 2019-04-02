package spring.mvc;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 */
public class StringToDateConverter implements Converter<String, Date> {


    @Override
    public Date convert(String source) {
        if(source !=null && "".equals(source.trim())){
            String str = source.trim();

            String tmpPattern = str.indexOf('/')!=-1 ? "MM/dd/yyyy HH:mm:ss" :
                    str.indexOf('-')!=-1 ? "yyyy-MM-dd HH:mm:ss" : null;

            try{
                if(tmpPattern == null){
                    return new Date(Long.valueOf(source));
                }

                SimpleDateFormat format = new SimpleDateFormat(tmpPattern);
                format.setLenient(false);
                return format.parse(source);

            }catch(Exception e){
                // TOLOG
            }
        }
        return null;
    }

}
