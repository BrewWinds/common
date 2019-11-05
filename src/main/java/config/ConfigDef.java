package config;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @Date: 2019/4/19 10:46
 * @Description:
 */
public class ConfigDef {


    private static final Pattern COMMA_WITH_WITHESPACE =
            Pattern.compile("\\s*,\\s*");

    private static final Object NO_DEFALUT_VALUE = new Object();

    private final Map<String, ConfigKey> configKeys;
    private final List<String> groups;
    private Set<String> configsWithNoParent;

    public ConfigDef(){
        configKeys = new LinkedHashMap<>();
        groups = new LinkedList<>();
        configsWithNoParent = null;
    }

    public Set<String> names(){
        return Collections.unmodifiableSet(configKeys.keySet());
    }

    public interface Validator{
        void ensureValid(String name, Object value);
    }

    public static class Range implements Validator{
        private final Number min;
        private final Number max;
        public Range(Number min, Number max){
            this.min = min;
            this.max = max;
        }

        public static Range atLeast(Number min){
            return new Range(min, null);
        }

        public static Range between(Number min, Number max){
            return new Range(min, max);
        }

        public void ensureValid(String name, Object o){
            if(o == null){
//                throw new ConfigException(name, null, "Value must not be null");
            }
            Number n = (Number)o;
            if(min!=null && n.doubleValue() < min.doubleValue()){
//                throw new ConfigException(name, o, "Value must be at least "+min);
            }
            if(max !=null && n.doubleValue() > max.doubleValue()){
//                throw new ConfigException(name, o, "Value must be no more than "+max);
            }

        }

        public enum Type{
            BOOLEAN, STRING, INT, SHORT, LONG, DOUBLE, LIST, CLASS, PASSWORD;
        }

        public enum Importance{
            HIGH, MEDIUM, LOW
        }

        public enum Width{
            NONE, SHORT, MEDIUM, LONG
        }
    }

    public interface Recommendr{

        List<Object> validValues(String name, Map<String, Object> parsedConfig);

        boolean visible(String name, Map<String, Object> parsedConfig);

    }

    public static class ConfigKey {

    }

    public static class ConfigException extends Exception{
        public ConfigException(String name, Object value, String message){
            super("Invalid value " + value + " for configuration " + name + (message == null ? "" : ": " + message));
        }
    }
}
