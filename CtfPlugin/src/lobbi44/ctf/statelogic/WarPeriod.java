package lobbi44.ctf.statelogic;

import lobbi44.ctf.CtfPlugin;
import lobbi44.ctf.statemachine.IStateCom;
import lobbi44.ctf.statemachine.InjectionVars;
import lobbi44.ctf.statemachine.Required;
import lobbi44.ctf.statemachine.StateBase;

/**
 * Created by HT on 23.04.2016.
 */
public class WarPeriod extends StateBase {

    @Required(Name = InjectionVars.PLUGIN_OBJECT)
    private CtfPlugin plugin;

    public WarPeriod(IStateCom com) {
        super(com);
    }

    @Override
    public void onStateActivated() {
        plugin.getServer().broadcastMessage("FIGHT!");
        plugin.getServer().getWorlds().get(0).setPVP(true);
    }

    @Override
    public void onStateExit() {

    }
}
