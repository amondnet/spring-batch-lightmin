package org.tuxdevelop.spring.batch.lightmin.admin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.quartz.CronExpression;

/**
 * @author Marcel Becker
 * @version 0.1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class JobSchedulerConfiguration extends AbstractConfiguration {

    private JobSchedulerType jobSchedulerType;
    private String cronExpression;
    private Long initialDelay;
    private Long fixedDelay;
    private TaskExecutorType taskExecutorType;
    private String beanName;
    private SchedulerStatus schedulerStatus;

    void validate() {
        if (jobSchedulerType == null) {
            throwExceptionAndLogError("jobSchedulerType must not be null");
        }
        if (taskExecutorType == null) {
            throwExceptionAndLogError("taskExecutorType must not be null");
        }
        if (JobSchedulerType.CRON.equals(jobSchedulerType)) {
            validateCron();
        } else if (JobSchedulerType.PERIOD.equals(jobSchedulerType)) {
            validatePeriod();
        } else {
            throwExceptionAndLogError("Unknown jobSchedulerType: " + jobSchedulerType);
        }
    }

    void validateCron() {
        if (cronExpression == null) {
            throwExceptionAndLogError("cronExpression must not be null for CRON Scheduler");
        } else if (!CronExpression.isValidExpression(cronExpression)) {
            throwExceptionAndLogError("cronExpression : " + cronExpression + " is not valid");
        }
        if (initialDelay != null) {
            throwExceptionAndLogError("initialDelay must not be set for CRON Scheduler");
        }
        if (fixedDelay != null) {
            throwExceptionAndLogError("fixedDelay must not be set for CRON Scheduler");
        }
    }

    void validatePeriod() {
        if (fixedDelay == null) {
            throwExceptionAndLogError("fixedDelay must not be null for PERIOD Scheduler");
        } else {
            if (fixedDelay <= 0) {
                throwExceptionAndLogError("fixedDelay must not be lower then 1");
            }
        }
        if (initialDelay != null && initialDelay <= 0) {
            throwExceptionAndLogError("initialDelay must not be lower then 1");
        }
        if (cronExpression != null) {
            throwExceptionAndLogError("cronExpression must not be set for PERIOD Scheduler");
        }
    }
}
