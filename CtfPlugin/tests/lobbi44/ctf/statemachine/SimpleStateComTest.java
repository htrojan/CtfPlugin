package lobbi44.ctf.statemachine;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SimpleStateComTest {


    @Test
    public void testSetActive() throws StateInstantiationException {
        SimpleStateCom com = new SimpleStateCom(new MockEventSystem());
        com.setActive(SimpleState.class);
        assertTrue(com.getActiveStateObject() instanceof SimpleState);
        assertSame(com, com.getActiveStateObject().com);
    }

    @Test(expected = StateInstantiationException.class)
    public void testSetActiveInjectionFails() throws StateInstantiationException {
        SimpleStateCom com = new SimpleStateCom(new MockEventSystem());
        com.setActive(SimpleInjectionState.class);
    }

    @Test
    public void testSetActiveInjection() throws StateInstantiationException, NoSuchFieldException, IllegalAccessException {
        SimpleStateCom com = new SimpleStateCom(new MockEventSystem());
        HashMap<String, Integer> map = new HashMap<>();
        com.provideObject(4, "int");
        com.provideObject(map, "map");
        com.setActive(SimpleInjectionState.class);

        SimpleInjectionState state = (SimpleInjectionState) com.getActiveStateObject();
        assertSame(map, state.injectedMap);
        Field field = SimpleInjectionState.class.getDeclaredField("toBeInjected");
        field.setAccessible(true);
        assertEquals(4, field.get(state));
    }
}