package lobbi44.ctf.statelogic;

import lobbi44.ctf.CtfPlugin;
import lobbi44.ctf.statemachine.*;
import lobbi44.ctf.util.Countdown;

/**
 * @author lobbi44
 */
public class PeacePeriod extends StateBase {

    @Required(Name = InjectionVars.PLUGIN_OBJECT)
    private CtfPlugin plugin;


    public PeacePeriod(IStateCom com) {
        super(com);
    }

    @Override
    public void onStateActivated() {
        plugin.getServer().broadcastMessage("Peace period entered!");
        plugin.getServer().getWorlds().get(0).setPVP(false);

        Countdown.forPlugin(plugin).repeat(11).delayBetween(20).run((i -> {
            plugin.getServer().broadcastMessage((10 - i) + " seconds");
            if (i >= 10) {
                try {
                    com.setActive(WarPeriod.class);
                } catch (StateInstantiationException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    @Override
    public void onStateExit() {
        plugin.getLogger().info("Peace state exited!");
    }
}
