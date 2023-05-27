package com.example.campaign.flight_service.fw.jobs.impl;

import com.example.campaign.flight_service.constants.Constants;
import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.exceptions.IgnoreJobException;
import com.example.campaign.flight_service.fw.jobs.Job;
import com.example.campaign.flight_service.fw.jobs.context.JobContext;
import com.example.campaign.flight_service.fw.service.BatchJobService;
import com.example.campaign.flight_service.fw.service.JobService;
import com.example.campaign.flight_service.fw.utils.BatchJobUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONObject;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cb-dhana
 */
public class CampaignJob extends Job {

    private static final Logger LOGGER = Logger.getLogger(CampaignJob.class.getName());

    private String inputFile;
    private BatchJobService batchJobService;
    private int jobStatus;

    public CampaignJob(JobContext jobContext, BatchJobService batchJobService, JobService jobService) {
        super(jobContext, jobService);
        this.batchJobService = batchJobService;
    }

    @Override
    protected void preCheck() throws Exception {

    }

    @Override
    protected void init() throws Exception {
        inputFile = jobContext.prop(Constants.JobContext.INPUT_FILE);
        jobStatus = jobContext.optInt(Constants.JobContext.STATUS, JobStatus.SCHEDULED.getCode());
    }

    @Override
    protected void execute() throws Exception {
        if (jobStatus == JobStatus.IN_PROGRESS.getCode()) {
            LOGGER.log(Level.INFO, "Job execution in progress");
            return;
        }
        File file = new File(inputFile);

        try (CSVParser csvParser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.RFC4180)) {
            long count = csvParser.stream().parallel().count();
            int offset = 0;

            while (offset <= count) {
                JSONObject context = new JSONObject(jobContext.getContext().toString());
                context.put(Constants.JobContext.OFFSET, offset);
                context.put(Constants.JobContext.BATCH_LIMIT, Constants.CampaignJob.BATCH_LIMIT);
                batchJobService.insertBatchJob(jobContext.getJobEntity(), context);
                offset += Constants.CampaignJob.BATCH_LIMIT;
            }

            jobContext.prop(Constants.JobContext.STATUS, JobStatus.IN_PROGRESS.getCode());
            jobService.updateJobStatus(jobContext.getJobEntity(), JobStatus.IN_PROGRESS);
        } catch (Exception e) {
            throw new IgnoreJobException("Exception while scheduling batch jobs. Hence aborting");
        }
        LOGGER.log(Level.INFO, "Batch jobs scheduled successfully," + jobContext.getJobEntity().getId());
    }

    @Override
    protected Timestamp getNextSchedule() throws Exception {
        if (BatchJobUtils.isJobCompleted(jobContext.getJobEntity(), batchJobService)) {
            handleJobCompletion();
            return null;
        }
        Date date = DateUtils.addSeconds(org.thymeleaf.util.DateUtils.createNow().getTime(), 10);
        LOGGER.log(Level.INFO, "Job rescheduled");
        return new Timestamp(date.getTime());
    }

    public void handleJobCompletion() {
        jobService.updateJobStatus(jobContext.getJobEntity(), JobStatus.COMPLETED);
        try {
            File file = new File(inputFile);
            file.delete();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Input file deletion failed", e);
        }
        LOGGER.log(Level.INFO, "Job execution completed");
    }
}
