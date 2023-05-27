package com.example.campaign.flight_service.fw.jobs;

import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.entity.JobEntity;
import com.example.campaign.flight_service.fw.exceptions.IgnoreJobException;
import com.example.campaign.flight_service.fw.jobs.context.JobContext;
import com.example.campaign.flight_service.fw.service.JobService;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cb-dhana
 */
public abstract class Job implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Job.class.getName());

    protected JobContext jobContext;
    protected JobService jobService;

    public Job(JobContext jobContext, JobService jobService) {
        this.jobContext = jobContext;
        this.jobService = jobService;
    }

    //############################# Abstract Methods Start ##################################//

    protected abstract void preCheck() throws Exception;

    protected abstract void init() throws Exception;

    protected abstract void execute() throws Exception;

    protected abstract Timestamp getNextSchedule() throws Exception;

    //############################# Abstract Methods End ##################################//

    //############################# Public Methods Start ##################################//


    @Override
    public void run() {
        LOGGER.log(Level.INFO, "JOB_INFO:: Job execution started");
        try {
            preCheck();
            init();
            execute();
        }catch (IgnoreJobException e) {
            jobService.updateJobStatus(jobContext.getJobEntity(), JobStatus.FAILED);
            LOGGER.log(Level.INFO, "JOB_SEVERE:: Job execution skipped", e);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "JOB_SEVERE:: Exception occurred. Trying to reschedule", e);
        }
        rescheduleIfRecurring();
        LOGGER.log(Level.INFO, "JOB_INFO:: Job execution completed");
    }

    public void rescheduleIfRecurring() {
        try {
            Timestamp nextSchedule = getNextSchedule();
            if (nextSchedule != null) {
                JobEntity jobEntity = jobContext.getJobEntity();
                jobEntity.setScheduledAt(nextSchedule);
                jobEntity.setStatus(JobStatus.RESCHEDULED.getCode());

                jobService.createOrUpdateJob(jobContext.getJobEntity());
                LOGGER.log(Level.INFO, "Job Rescheduled");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "JOB_SEVERE:: Job Reschedule Failed");
        }
    }

    //############################# Public Methods End ##################################//
}
