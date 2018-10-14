package org.tuxdevelop.spring.batch.lightmin.admin.scheduler;

import org.tuxdevelop.spring.batch.lightmin.domain.SchedulerStatus;

/**
 * @author Marcel Becker
 * @see AbstractScheduler
 * @since 0.1
 */
public interface Scheduler {

    /**
     * triggers the scheduler
     */
    void schedule();

    /**
     * terminates the scheduler
     */
    void terminate();

    /**
     * retrieves the current {@link org.tuxdevelop.spring.batch.lightmin.domain.SchedulerStatus}
     *
     * @return {@link org.tuxdevelop.spring.batch.lightmin.domain.SchedulerStatus}
     */
    SchedulerStatus getSchedulerStatus();
}
