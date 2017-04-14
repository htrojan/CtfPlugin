package lobbi44.ctf.statemachine;

/**
 * Created by HT on 09.04.2016.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates whether a state object's variables
 * have to be injected by the StateCommunicator
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {

    /**
     * This is the name the requested object has.
     * If no name is given it is tried to be matched to
     * a suiting object known to the StateCommunicator
     */
    String Name();
}
