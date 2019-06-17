package util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 */
public class RegexUtils {

    private RegexUtils(){}

    private static final LoadingCache<String, Pattern> PATTERNS =
            CacheBuilder.newBuilder().softValues().build(
                    new CacheLoader<String, Pattern>() {
                        @Override
                        public Pattern load(String pattern) throws Exception {
                            return Pattern.compile(pattern);
                        }
                    }
            );

    public static final String REGEXP_IP = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}"
            + "(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";

    public static final String REGEXP_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)"
            + "[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    public static final String REGEXP_MOBILE = "^\\s*(((\\+)?86)|(\\((\\+)?86\\)))?1\\d{10}\\s*$";
    public static final Pattern PATTERN_MOBILE = Pattern.compile(REGEXP_MOBILE);


    public static final Pattern PATTERN_EMAIL = Pattern.compile(REGEXP_EMAIL);


    public static final Pattern PATTERN_IP = Pattern.compile(REGEXP_IP);

    public static boolean isIp(String text){
        return text!=null && PATTERN_IP.matcher(text).matches();
    }

    public static boolean isEmail(String text){
        return text!=null && PATTERN_EMAIL.matcher(text).matches();
    }

    public static boolean isMobilePhone(String text){
        return text!=null && PATTERN_MOBILE.matcher(text).matches();
    }

    public static String findFirst(String orginalStr, String regex){
        return findGroup(orginalStr, regex, 0);
    }

    public static String findGroup(String originalStr, String regex, int group){
        if(originalStr == null || regex == null){
            return StringUtils.EMPTY;
        }

        try {
            Matcher matcher = PATTERNS.get(regex).matcher(originalStr);
            return matcher.find() ? matcher.group(group) : StringUtils.EMPTY;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean matches(String originalStr, String regex){
        if(originalStr == null || regex == null){
            return false;
        }

        try {
            return PATTERNS.get(regex).matcher(originalStr).matches();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
