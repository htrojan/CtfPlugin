package lobbi44.ctf.statemachine;

import java.util.HashMap;

/**
 * Created by HT on 23.04.2016.
 */
public class SimpleInjectionState extends StateBase {

    @Required(Name = "int")
    private int toBeInjected;

    @Required(Name = "map")
    public HashMap<String, Integer> injectedMap;

    public SimpleInjectionState(IStateCom com) {
        super(com);
    }

    @Override
    public void onStateActivated() {

    }

    @Override
    public void onStateExit() {

    }
}
