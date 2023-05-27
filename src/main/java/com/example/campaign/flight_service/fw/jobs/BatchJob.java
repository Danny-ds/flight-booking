package com.example.campaign.flight_service.fw.jobs;

import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.exceptions.IgnoreJobException;
import com.example.campaign.flight_service.fw.jobs.context.BatchJobContext;
import com.example.campaign.flight_service.fw.jobs.context.JobContext;
import com.example.campaign.flight_service.fw.service.BatchJobService;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cb-dhana
 */
public abstract class BatchJob {

    private static final Logger LOGGER = Logger.getLogger(Job.class.getName());

    protected BatchJobContext jobContext;
    protected BatchJobService batchJobService;

    public BatchJob(BatchJobContext jobContext, BatchJobService batchJobService) {
        this.jobContext = jobContext;
        this.batchJobService = batchJobService;
    }

    //############################# Abstract Methods Start ##################################//

    protected abstract void init() throws Exception;

    protected abstract void execute() throws Exception;

    //############################# Abstract Methods End ##################################//

    //############################# Public Methods Start ##################################//

    public void run() throws Exception {
        LOGGER.log(Level.INFO, "BATCH_JOB_INFO:: Job execution started");
        try {
            init();
            execute();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "JOB_SEVERE:: Exception occurred", e);
            batchJobService.updateBatchJobStatus(jobContext.getJobEntity(), JobStatus.FAILED);
        }
        LOGGER.log(Level.INFO, "JOB_INFO:: Job execution completed");
    }

    //############################# Public Methods End ##################################//
}
