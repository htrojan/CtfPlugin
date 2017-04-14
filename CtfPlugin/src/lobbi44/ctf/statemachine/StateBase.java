package lobbi44.ctf.statemachine;

import org.bukkit.event.Listener;

/**
 * The class all states have to inherit.
 * The Bukkit event system is used for distributing messages
 * and events
 */
public abstract class StateBase implements Listener {
    /**
     * This communicator has to be used to send/receive
     * messages to/from other states and to change
     * the active state
     */
    protected IStateCom com;

    /**
     * This variable is set by the state communicator and
     * is true if this state is currently activated
     */
    private boolean active;

    public StateBase(IStateCom com) {
        this.com = com;
    }

    /**
     * This method is called whenever the states becomes active
     */
    public abstract void onStateActivated();

    /**
     * This method is called whenever the states is left
     */
    public abstract void onStateExit();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean newActive) {
        this.active = newActive;
    }

    protected void switchState(Class<? extends StateBase> c) {
        try {
            com.setActive(c);
        } catch (StateInstantiationException e) {
            e.printStackTrace();
        }
    }
}
