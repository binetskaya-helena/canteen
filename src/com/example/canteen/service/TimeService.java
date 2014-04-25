package com.example.canteen.service;

import java.util.Date;

/** A service that provides various time functions specific for the hosting platform.
 */
public interface TimeService {
    /** @return The current date on the hosting platform. */
    Date now();

    /** Schedules the given action to be fired on the given date */
    void schedule(Date when, Runnable action);

    /** Cancels execution of the given action if it was scheduled with this service. */
    void cancel(Runnable action);
}
