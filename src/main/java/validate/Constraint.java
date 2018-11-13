package validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Constraint {

    String regExp() default "";

    boolean notNull() default true;

    boolean notBlank() default false;

    boolean notEmpty() default false;

    long max() default Long.MAX_VALUE;

    long min() default Long.MIN_VALUE;

    int maxLen() default -1;

    int minLen() default -1;

    String pattern() default "";

    String msg() default "";

    Tense tense() default Tense.ANY;

    int[] series() default {};

    enum Tense{
        PAST, FUTURE, ANY;
    }

}
