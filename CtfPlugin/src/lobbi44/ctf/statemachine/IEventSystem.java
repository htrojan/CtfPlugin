package lobbi44.ctf.statemachine;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * Created by HT on 28.03.2016.
 */
public interface IEventSystem {
    void raiseEvent(Event event);

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);
}
