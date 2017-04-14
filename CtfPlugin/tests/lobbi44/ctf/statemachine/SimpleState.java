package lobbi44.ctf.statemachine;

/**
 * Created by HT on 23.04.2016.
 */

class SimpleState extends StateBase {

    public SimpleState(IStateCom com) {
        super(com);
    }

    public IStateCom getCommunicator() {
        return com;
    }

    @Override
    public void onStateActivated() {

    }

    @Override
    public void onStateExit() {

    }
}
