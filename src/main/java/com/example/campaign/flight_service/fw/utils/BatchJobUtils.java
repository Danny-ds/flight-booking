package com.example.campaign.flight_service.fw.utils;

import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.entity.BatchJobEntity;
import com.example.campaign.flight_service.fw.entity.JobEntity;
import com.example.campaign.flight_service.fw.service.BatchJobService;

import java.util.List;

/**
 * @author cb-dhana
 */
public class BatchJobUtils {

    public static boolean isJobCompleted(JobEntity jobEntity, BatchJobService batchJobService) {
        List<BatchJobEntity> batchJobs = batchJobService.getBatchJobs(jobEntity);
        return batchJobs.stream().noneMatch(batchJob -> batchJob.getStatus() == JobStatus.SCHEDULED.getCode());
    }
}
