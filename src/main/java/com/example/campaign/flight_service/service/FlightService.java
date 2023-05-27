package com.example.campaign.flight_service.service;

import com.example.campaign.flight_service.constants.Constants;
import com.example.campaign.flight_service.enums.AppJobType;
import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.service.BatchJobService;
import com.example.campaign.flight_service.fw.utils.BatchJobUtils;
import com.example.campaign.flight_service.models.FlightServiceMeta;
import com.example.campaign.flight_service.models.response.CampaignStatus;
import com.example.campaign.flight_service.models.response.PromotionalCampaign;
import com.example.campaign.flight_service.fw.entity.JobEntity;
import com.example.campaign.flight_service.fw.service.JobService;
import com.example.campaign.flight_service.fw.utils.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;

/**
 * @author cb-dhana
 */
@Service
public class FlightService {

    private JobService jobService;
    private FileUtils fileUtils;
    private BatchJobService batchJobService;

    @Autowired
    public FlightService(JobService jobService, FileUtils fileUtils, BatchJobService batchJobService) {
        this.jobService = jobService;
        this.fileUtils = fileUtils;
        this.batchJobService = batchJobService;
    }

    public FlightServiceMeta getFlightServiceMeta() {
        return FlightServiceMeta.builder()
                .flightName("AZ Airlines")
                .services(Arrays.asList("Promotional Campaign"))
                .build();
    }

    public PromotionalCampaign processPromotionalCampaign(MultipartFile file) throws Exception {
        String path = fileUtils.storeFile(file, Constants.File.CAMPAIGN_FILE, Constants.File.CSV);
        JSONObject context = new JSONObject();
        context.put(Constants.JobContext.INPUT_FILE, path);
        context.put(Constants.JobContext.SUCCESS_FILE, fileUtils.createTempOutputFile(Constants.File.SUCCESS, Constants.File.CSV));
        context.put(Constants.JobContext.FAILURE_FILE, fileUtils.createTempOutputFile(Constants.File.FAILURE, Constants.File.CSV));

        JobEntity jobEntity = JobEntity.builder()
                .context(context.toString())
                .status(JobStatus.SCHEDULED.getCode())
                .jobType(AppJobType.CAMPAIGN_JOB.code)
                .scheduledAt(new Timestamp(Instant.now().getEpochSecond()))
                .build();
        jobService.createOrUpdateJob(jobEntity);

        PromotionalCampaign response = new PromotionalCampaign();
        response.setId(jobEntity.getId());
        response.setCode(HttpStatus.OK.value());
        response.setMessage("File processing started successfully. Please check the status after few minutes");
        return response;
    }

    public CampaignStatus handleCampaignStatus(Long jobId) throws Exception {
        JobEntity jobEntity = jobService.getJob(jobId).get();
        boolean isCompleted = BatchJobUtils.isJobCompleted(jobEntity, batchJobService);

        CampaignStatus campaignStatus = new CampaignStatus();
        campaignStatus.setCode(HttpStatus.OK.value());
        if (!isCompleted) {
            campaignStatus.setMessage("File is processing. Please check after few minutes");
            return campaignStatus;
        }

        campaignStatus.setMessage("File processed successfully");
        JSONObject context = new JSONObject(jobEntity.getContext());
        campaignStatus.setSuccessFilePath(context.getString(Constants.JobContext.SUCCESS_FILE));
        campaignStatus.setFailureFilePath(context.getString(Constants.JobContext.FAILURE_FILE));
        return campaignStatus;
    }
}
