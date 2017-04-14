package lobbi44.ctf.statemachine;

/**
 * Created by HT on 09.04.2016.
 */
public class StateInstantiationException extends Throwable {
    public StateInstantiationException() {
        super();
    }

    public StateInstantiationException(String message) {
        super(message);
    }

    public StateInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateInstantiationException(Throwable cause) {
        super(cause);
    }

    protected StateInstantiationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
