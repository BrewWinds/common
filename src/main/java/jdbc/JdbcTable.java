package jdbc;

import java.lang.annotation.*;

/**
 * @Auther: 01378178
 * @Date: 2018/11/15 15:11
 * @Description:
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JdbcTable {

    String namespace() default "";

    String tableName() default "";


}
