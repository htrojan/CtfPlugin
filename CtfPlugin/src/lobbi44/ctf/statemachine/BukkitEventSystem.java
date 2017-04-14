package lobbi44.ctf.statemachine;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Created by HT on 28.03.2016.
 */
public class BukkitEventSystem implements IEventSystem {
    private Plugin plugin;

    public BukkitEventSystem(Plugin plugin) {

        this.plugin = plugin;
    }

    @Override
    public void raiseEvent(Event event) {
        plugin.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void registerListener(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

}
