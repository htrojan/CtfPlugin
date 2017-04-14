package lobbi44.ctf.statemachine;

/**
 * Created by HT on 09.04.2016.
 */
public class InjectionException extends Throwable {

    public InjectionException() {
        super();
    }

    public InjectionException(String message) {
        super(message);
    }

    public InjectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InjectionException(Throwable cause) {
        super(cause);
    }

    protected InjectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
