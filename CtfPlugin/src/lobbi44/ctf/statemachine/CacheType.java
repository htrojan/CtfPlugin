package lobbi44.ctf.statemachine;

/**
 * Created by HT on 09.04.2016.
 */
public enum CacheType {
    /**
     * This object is not going to be cached. Bad for performance
     */
    NONE,
    /**
     * This object retains its state, but does not get informed about events
     * that happen during it is inactive
     */
    RETAIN_STATE,
    /**
     * This object retains its state and is informed about events that
     * happen even if it is not activated
     */
    ALWAYS_ACTIVE;
}
