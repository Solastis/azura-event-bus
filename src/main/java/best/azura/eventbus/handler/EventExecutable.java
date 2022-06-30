package best.azura.eventbus.handler;

import best.azura.eventbus.core.Event;
import best.azura.eventbus.core.EventPriority;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EventExecutable {

    //Parental object
    private final Object parent;

    //MethodHandler instance for registered methods
    private MethodHandler method;

    //ListenerHandler instance for registered listeners
    private ListenerHandler<? extends Event> listener;

    //Priority inside event system
    private final EventPriority eventPriority;

    private final int priority;

    public EventExecutable(final Method method, final Field field, final Object parent, final EventPriority eventPriority) {
        this.parent = parent;
        this.eventPriority = eventPriority;
        priority = eventPriority.getPriority();
        //Registering a listener if the field isn't null
        if (field != null) {
            try {
                field.setAccessible(true);
                this.listener = new ListenerHandler<>(field.getGenericType(), (Listener<?>) field.get(parent));
            } catch (Exception e) {
                this.listener = null;
                e.printStackTrace();
            }
        } else {
            this.listener = null;
        }
        //Registering the method if it isn't null
        if (method != null && method.getParameterCount() == 1) {
            this.method = new MethodHandler(method, parent);
        }
    }

    public EventExecutable(final Method method, final Object parent, final EventPriority eventPriority) {
        this(method, null, parent, eventPriority);
    }

    public EventExecutable(final Field field, final Object parent, final EventPriority eventPriority) {
        this(null, field, parent, eventPriority);
    }

    public MethodHandler getMethod() {
        return method;
    }

    public ListenerHandler<? extends Event> getListener() {
        return listener;
    }

    public Object getParent() {
        return parent;
    }

    public EventPriority getEventPriority() {
        return eventPriority;
    }

    public int getPriority() {
        return priority;
    }
}