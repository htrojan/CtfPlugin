package lobbi44.ctf.statemachine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by HT on 09.04.2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * This is used on state objects if they have to be active
 * the whole time and ensures that they are not destroyed
 */
public @interface CacheMode {
    CacheType cacheType() default CacheType.RETAIN_STATE;
}
