package lobbi44.ctf.util;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author lobbi44
 *         Defines a spawn location with its center (x, z) and range in blocks, whereby the range is
 *         read as a radius, but for a square. So r = x/2 whereas r is the radius of a circle and x
 *         the length of the square
 */
public class SpawnLoc {
    public SpawnLoc(int x, int z, int range, World world) {
        this.x = x;
        this.z = z;
        this.range = range;
        this.world = world;
    }

    public int x, z, range;
    public World world;

    public Location toBukkitLoc() {
        double y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }
}
