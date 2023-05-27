package com.example.campaign.flight_service.fw.listeners;

import com.example.campaign.flight_service.fw.jobs.executor.JobExecutor;
import com.example.campaign.flight_service.fw.service.BatchJobService;
import com.example.campaign.flight_service.fw.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cb-dhana
 */
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = Logger.getLogger(ApplicationReadyListener.class.getName());

    private JobService jobService;
    private BatchJobService batchJobService;

    @Autowired
    public ApplicationReadyListener(JobService jobService, BatchJobService batchJobService) {
        this.jobService = jobService;
        this.batchJobService = batchJobService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Runnable runnable = new JobExecutor(jobService, batchJobService);
        Thread thread = new Thread(runnable);
        thread.start();
        LOGGER.log(Level.INFO, "Job service started");
    }
}
