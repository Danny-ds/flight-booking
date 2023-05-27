
package com.example.campaign.flight_service.fw.service;

import com.example.campaign.flight_service.constants.Constants;
import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.entity.BatchJobEntity;
import com.example.campaign.flight_service.fw.entity.JobEntity;
import com.example.campaign.flight_service.fw.repo.IBatchJobsRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author cb-dhana
 */

@Service
public class BatchJobService {
    private IBatchJobsRepo batchJobsRepo;
    @Value("${campaign.topic}")
    private String topic;
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public BatchJobService(KafkaTemplate<String, String> kafkaTemplate, IBatchJobsRepo batchJobsRepo) {
        this.kafkaTemplate = kafkaTemplate;
        this.batchJobsRepo = batchJobsRepo;
    }

    public Optional<BatchJobEntity> getJob(Long batchJobId) throws Exception {
        return batchJobsRepo.findById(batchJobId);
    }

    public void insertBatchJob(JobEntity jobEntity, JSONObject context) {
        BatchJobEntity batchJob = BatchJobEntity.builder()
                .jobType(jobEntity.getJobType())
                .jobEntity(jobEntity)
                .context(context.toString())
                .status(JobStatus.SCHEDULED.getCode())
                .build();
        createOrUpdateJob(batchJob);
    }

    public BatchJobEntity createOrUpdateJob(BatchJobEntity batchJob) {
        batchJobsRepo.save(batchJob);

        JSONObject message = new JSONObject();
        message.put(Constants.Job.BATCH_JOB_ID, batchJob.getId());
        message.put(Constants.Job.BATCH_JOB_TYPE, batchJob.getJobType());
        produce(message.toString());
        return batchJob;
    }

    public BatchJobEntity updateBatchJobStatus(BatchJobEntity batchJob, JobStatus jobStatus) {
        batchJob.setStatus(jobStatus.getCode());
        batchJobsRepo.save(batchJob);
        return batchJob;
    }

    public List<BatchJobEntity> getBatchJobs(JobEntity jobEntity) {
        return batchJobsRepo.findByJobEntity(jobEntity);
    }
    private void produce(String message) {
        kafkaTemplate.send(topic, message);
    }
}
