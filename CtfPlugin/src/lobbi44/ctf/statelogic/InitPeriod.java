package lobbi44.ctf.statelogic;

import lobbi44.ctf.CtfPlugin;
import lobbi44.ctf.statemachine.IStateCom;
import lobbi44.ctf.statemachine.InjectionVars;
import lobbi44.ctf.statemachine.Required;
import lobbi44.ctf.statemachine.StateBase;
import lobbi44.ctf.util.Countdown;
import lobbi44.ctf.util.OfflinePlayerHelper;
import lobbi44.ctf.util.SpawnLoc;
import lobbi44.ctf.util.TeamHelper;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;

/**
 * @author lobbi44
 */
public class InitPeriod extends StateBase {

    @Required(Name = InjectionVars.PLUGIN_OBJECT)
    private CtfPlugin plugin;

    @Required(Name = InjectionVars.MAIN_SCOREBOARD)
    private Scoreboard scoreboard;

    @Required(Name = InjectionVars.PLAYER_HELPER)
    private OfflinePlayerHelper playerHelper;

    @Required(Name = InjectionVars.TEAM_HELPER)
    private TeamHelper teamHelper;

    public InitPeriod(IStateCom com) {
        super(com);
    }

    @Override
    public void onStateActivated() {
        plugin.getLogger().info("The initial ctf state has been entered");
        plugin.initializeTeams();

        teamHelper.tpTeamsToSpawns(playerHelper);

        Countdown.forPlugin(plugin).repeat(11).delayBetween(20).run((i -> {
            if (i >= 10)
                switchState(PeacePeriod.class);
            else
                plugin.getServer().broadcastMessage("Game will begin in " + (10 - i) + " seconds...");

        }));
    }


    @Override
    public void onStateExit() {
        //tp players to
        for (Map.Entry<Team, SpawnLoc> entry : plugin.getTeamHelper().getTeamSpawns().entrySet()) {
            for (OfflinePlayer player : entry.getKey().getPlayers()) {
                plugin.getTpHelper().tpPlayer(player, entry.getValue().toBukkitLoc());
            }
        }
        plugin.getServer().broadcastMessage("The game has started!");
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage("Welcome to this CTF server. The game will start soon");
    }

    /**
     * This EventHandler prevents any participating players to move away from their current locations
     */
    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (plugin.playerIsParticipating(event.getPlayer())) {
            event.setCancelled(true);
        }
        event.setCancelled(true);
    }
}
