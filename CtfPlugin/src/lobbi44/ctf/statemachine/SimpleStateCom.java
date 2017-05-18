package lobbi44.ctf.statemachine;

import org.bukkit.event.Listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of the IStateCom interface
 * for providing a form communication and a supervisor for
 * all states
 */
public class SimpleStateCom implements IStateCom, Listener {

    /**
     * The event system used for communication
     */
    private IEventSystem eventSystem;
    private StateBase activeState;
    /**
     * Stores the corresponding objects for each state(-class)
     */
    private Map<Class<? extends StateBase>, StateBase> stateMap = new HashMap<>();

    private Map<String, Object> providedObjects = new HashMap<>();

    public SimpleStateCom(IEventSystem eventSystem) {
        this.eventSystem = eventSystem;
    }


    @Override
    public void setActive(Class<? extends StateBase> newState) throws StateInstantiationException {
        if (activeState != null) {
            activeState.onStateExit();
            activeState.setActive(false);
            eventSystem.unregisterListener(activeState);
        }
        try {
            activeState = getStateObject(newState);
        } catch (InjectionException e) {
            throw new StateInstantiationException(e);
        }
        activeState.setActive(true);
        activeState.onStateActivated();
    }

    @Override
    public Class<? extends StateBase> getActive() {
        return activeState.getClass();
    }

    @Override
    public StateBase getActiveStateObject() {
        return activeState;
    }

    @Override
    /**
     * Used to clear all states. The beginning state has to be set again!
     */
    public void resetAll() {
        stateMap.clear();
        activeState = null;
    }

    @Override
    public void provideObject(Object object, String name) {
        if (providedObjects.containsKey(name))
            throw new IllegalArgumentException("The name \"" + name + "\" does already exist");
        providedObjects.put(name, object);
        //System.out.println("Registered " + object.toString() + " as " + name);
    }

    private StateBase getStateObject(Class<? extends StateBase> state) throws InjectionException {
        if (!stateMap.containsKey(state)) {
            stateMap.put(state, instantiateState(state));
        }
        return stateMap.get(state);
    }

    /**
     * Creates an object for the state, injects all necessary variables and registers it at the event system
     *
     * @param state The State to be instantiated
     * @return The State object
     */
    private StateBase instantiateState(Class<? extends StateBase> state) throws InjectionException {
        try {
            if (state.isLocalClass()) {
                throw new InjectionException("Private/Local classes are not supported as State-classes currently");
                /**todo: Handle private classes better -> Their constructor takes an additional argument consisting
                 ** of the class they are defined in**/

            }
            Constructor<? extends StateBase> constructor = state.getConstructor(IStateCom.class);
            StateBase object = constructor.newInstance(this);

            injectValues(object);
            eventSystem.registerListener(object);
            return object;
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            //todo: Don't handle the error here, give it through the stack-trace where it is needed and change the return null
            e.printStackTrace();
        }
        return null;
    }

    private void injectValues(Object object) throws InjectionException {
        for (Field field : object.getClass().getDeclaredFields()) {
            Required annotation = field.getAnnotation(Required.class);
            if (annotation == null)
                continue;
            if (!providedObjects.containsKey(annotation.Name()))
                throw new InjectionException("The result \"" + annotation.Name() + "\" could not be injected into \"" +
                        field.getName() + "\" as no object with such name has been provided.");
            try {
                field.setAccessible(true);
                field.set(object, providedObjects.get(annotation.Name()));
            } catch (IllegalAccessException e) {
                throw new InjectionException("The field couldn't be accessed for injection.", e);
            }
        }
    }
}
