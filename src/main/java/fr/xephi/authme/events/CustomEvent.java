package fr.xephi.authme.events;

import org.bukkit.event.Event;

/**
 * The parent of all AuthMe events.
 */
public abstract class CustomEvent extends Event {

    /**
     * Constructor.
     */
    public CustomEvent() {
        super(false);
    }

    /**
     * Constructor.
     */
    public CustomEvent(boolean isAsync) {
        super(isAsync);
    }

}
