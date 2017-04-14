package lobbi44.ctf.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author lobbi44
 */
public class TeamHelper {

    private Scoreboard scoreboard;
    private World spawnWorld;
    private Map<Team, SpawnLoc> teamSpawns = new HashMap<>();
    private Logger logger;

    public TeamHelper(Scoreboard scoreboard, World teamWorld, Logger logger) {
        this.scoreboard = scoreboard;
        this.spawnWorld = teamWorld;
        this.logger = logger;
    }

    public Map<Team, SpawnLoc> getTeamSpawns() {
        return teamSpawns;
    }

    /**
     * Calculates the team spawns
     */
    public void initializeTeams(int spawnRange) {
        int num = scoreboard.getTeams().size();

        if (num <= 0)
            throw new IllegalStateException("There are no teams to be initialized");

        Iterator<Team> iterator = scoreboard.getTeams().iterator();
        //The radius of the circle the team spawns will be arranged around
        int radius = spawnRange * num / 2;
        //todo: Is this a float in the end?
        float angleOffset = 360f / num;

        Location spawn = spawnWorld.getSpawnLocation();

        for (int i = 0; i < num; i++) {
            int x = ((int) Math.round(Math.cos(i * angleOffset) * radius)) + spawn.getBlockX();
            int z = ((int) Math.round(Math.sin(i * angleOffset) * radius)) + spawn.getBlockZ();
            teamSpawns.put(iterator.next(), new SpawnLoc(x, z, spawnRange, spawnWorld));
            spawnWorld.getHighestBlockAt(x, z).setType(Material.STATIONARY_LAVA);
        }
    }


    public void tpTeamsToSpawns(OfflinePlayerHelper tpHelper) {
        for (Map.Entry<Team, SpawnLoc> teamEntry : teamSpawns.entrySet()) {
            for (OfflinePlayer player : teamEntry.getKey().getPlayers()) {
                tpHelper.tpPlayer(player, teamEntry.getValue().toBukkitLoc());
                logger.warning("Tp'ed player!");
            }
        }
    }
}
