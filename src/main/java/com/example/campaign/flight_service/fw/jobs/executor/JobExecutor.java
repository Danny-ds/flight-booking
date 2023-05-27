package com.example.campaign.flight_service.fw.jobs.executor;

import com.example.campaign.flight_service.enums.AppJobType;
import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.entity.JobEntity;
import com.example.campaign.flight_service.fw.exceptions.IgnoreJobException;
import com.example.campaign.flight_service.fw.jobs.Job;
import com.example.campaign.flight_service.fw.jobs.context.JobContext;
import com.example.campaign.flight_service.fw.jobs.impl.CampaignJob;
import com.example.campaign.flight_service.fw.service.BatchJobService;
import com.example.campaign.flight_service.fw.service.JobService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cb-dhana
 */
public class JobExecutor implements Runnable {

    private JobService jobService;
    private BatchJobService batchJobService;
    private static final Logger LOGGER = Logger.getLogger(JobExecutor.class.getName());

    public JobExecutor(JobService jobService, BatchJobService batchJobService) {
        this.jobService = jobService;
        this.batchJobService = batchJobService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                scheduleJobs();
                Thread.sleep(2000L);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Exception while scheduling jobs due to " + e.getMessage());
            }
        }
    }


    private void scheduleJobs() {
        List<JobEntity> jobs = jobService.fetchJobsToExecute();
        for (JobEntity jobEntity : jobs) {
            executeJob(jobEntity);
        }
    }

    public void executeJob(JobEntity jobEntity) {
        JobContext jobContext = JobContext.builder()
                .context(new JSONObject(jobEntity.getContext()))
                .jobEntity(jobEntity)
                .jobId(jobEntity.getId())
                .build();
        try {
            Runnable runnable = getJob(jobEntity.getJobType(), jobContext);
            Thread thread = new Thread(runnable);
            thread.start();
            jobService.updateJobStatus(jobEntity, JobStatus.STARTED);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Job Execution Failed due to: " + e.getMessage() + " , JobId: " + jobEntity.getId());
        }
    }

    private final Job getJob(int jobType, JobContext jobContext) throws Exception {
        AppJobType appJobType = AppJobType.getJob(jobType);

        switch (appJobType) {
            case CAMPAIGN_JOB:
                return new CampaignJob(jobContext, batchJobService, jobService);
            default:
                throw new IgnoreJobException("Invalid Job Type");
        }
    }
}
