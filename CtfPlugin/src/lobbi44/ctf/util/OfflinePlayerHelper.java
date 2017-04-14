package lobbi44.ctf.util;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author lobbi44
 *         This class supports the porting of offline players.
 *         The events have to be registered first!
 */
public class OfflinePlayerHelper implements Listener {

    private Map<OfflinePlayer, OfflineJob> tpJobs;
    private Logger logger;

    //todo: Pass a save file here
    //todo: When shutting down the server, this has to be saved!
    public OfflinePlayerHelper(Map<OfflinePlayer, OfflineJob> jobs, Logger logger) {
        tpJobs = jobs;
        this.logger = logger;
    }

    public void doJob(OfflinePlayer player, OfflineJob job) {
        if (player.isOnline()) {
            job.doJob(player.getPlayer());
            logger.warning("Job executed");
        }
        else
            tpJobs.put(player, job);
    }

    public void tpPlayer(OfflinePlayer player, Location loc) {
        doJob(player, new TpJob(loc));
    }


    /**
     * Checks if any actions were planned for a newly joined player
     */
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (tpJobs.containsKey(p)) {
            tpJobs.get(p).doJob(p);
            tpJobs.remove(p);
        }
    }
}

interface OfflineJob {
    //todo: Add success variable? Error handler? Lambdas?
    boolean doJob(Player p);
}

class TpJob implements OfflineJob {

    private Location loc;

    public TpJob(Location loc) {
        this.loc = loc;
    }

    @Override
    public boolean doJob(Player p) {
        p.teleport(loc);
        return true;
    }
}

