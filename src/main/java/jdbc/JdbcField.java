package jdbc;

import java.lang.annotation.*;

/**
 * @Date: 2018/11/15 15:05
 * @Description:
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JdbcField {

    String column() default "";

    String type() default "varchar";

    int len() default 50;

    boolean primaryKey() default false;

    boolean autoIncrement() default false;

    // To Complete
    enum DataType{
        INT, VARCHAR, DATETIME;
    }
}
