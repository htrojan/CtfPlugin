package lobbi44.ctf.statemachine;

/**
 * IStateCommunication
 * Used by states to communicate with each other and retrieve and store
 * information
 */
public interface IStateCom {
    /**
     * changes the currently active state
     *
     * @param newState the class of the new state to be activated
     */
    void setActive(Class<? extends StateBase> newState) throws StateInstantiationException;

    /**
     * Returns the currently active state
     *
     * @return The class of the active state
     */
    Class<? extends StateBase> getActive();

    /**
     * Returns the currently active state
     *
     * @return The object of the active state
     */
    StateBase getActiveStateObject();

    /**
     * Resets the whole state machine to the default state
     */
    void resetAll();

    /**
     * This method is called to provide objects for dependency injection
     *
     * @param object The object to be provided
     * @param name   The internal name used to request this object
     */
    void provideObject(Object object, String name);
}
