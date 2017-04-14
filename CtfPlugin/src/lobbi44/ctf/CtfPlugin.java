package lobbi44.ctf;


import lobbi44.ctf.commands.CtfCommands;
import lobbi44.ctf.commands.TeamCommands;
import lobbi44.ctf.statemachine.BukkitEventSystem;
import lobbi44.ctf.statemachine.InjectionVars;
import lobbi44.ctf.statemachine.SimpleStateCom;
import lobbi44.ctf.util.OfflinePlayerHelper;
import lobbi44.ctf.util.TeamHelper;
import lobbi44.kt.command.CommandFramework;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

/**
 *
 */
public class CtfPlugin extends JavaPlugin {

    private CommandFramework framework;
    private SimpleStateCom stateCom;
    private Scoreboard scoreboard;

    private static final int spawnRange = 50;
    //todo: This has to be defined in the configs
    /**
     * The world the players will spawn in
     **/
    private World spawnWorld;

    public OfflinePlayerHelper getTpHelper() {
        return tpHelper;
    }

    private OfflinePlayerHelper tpHelper;
    private TeamHelper teamHelper;

    @Override
    public void onEnable() {
        scoreboard = getServer().getScoreboardManager().getMainScoreboard();

        getLogger().info("onEnable() called");
        getLogger().info(getServer().getWorlds().size() + " worlds found");
        getServer().getLogger().warning("World = " + spawnWorld);

        //register the events of the tpHelper
        tpHelper = new OfflinePlayerHelper(new HashMap<>(), getLogger());
        getServer().getPluginManager().registerEvents(tpHelper, this);
        //register remaining helper objects
        teamHelper = new TeamHelper(scoreboard, spawnWorld, getLogger());

        initStateCommunicator();
        initCommandFramework();
    }

    private void initCommandFramework() {
        framework = new CommandFramework(this);
        framework.registerCommands(new TeamCommands(scoreboard, getLogger()));
        framework.registerCommands(new CtfCommands(this, stateCom));

        //framework.registerHelp();
    }

    private void initStateCommunicator() {
        stateCom = new SimpleStateCom(new BukkitEventSystem(this));
        stateCom.provideObject(this, InjectionVars.PLUGIN_OBJECT);
        stateCom.provideObject(scoreboard, InjectionVars.MAIN_SCOREBOARD);
        stateCom.provideObject(tpHelper, InjectionVars.PLAYER_HELPER);
        stateCom.provideObject(teamHelper, InjectionVars.TEAM_HELPER);
    }

    @Override
    public void onDisable() {
        //// TODO: 30.05.2016 Save the state of the plugin before shutting down
    }

    public TeamHelper getTeamHelper() {
        return teamHelper;
    }

    public void initializeTeams() {
        teamHelper.initializeTeams(spawnRange);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public boolean playerIsParticipating(OfflinePlayer player) {
        return scoreboard.getPlayerTeam(player) != null;
    }




}
