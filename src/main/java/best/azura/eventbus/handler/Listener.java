package best.azura.eventbus.handler;

/**
 * @author Solastis
 * DATE:19.12.21
 */
public interface Listener<Event> {

    /**
     * Method for calling an event
     *
     * @param event event that should be called
     */
    void call(final Event event);
}
