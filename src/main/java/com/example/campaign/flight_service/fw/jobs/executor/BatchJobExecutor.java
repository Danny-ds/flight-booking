package com.example.campaign.flight_service.fw.jobs.executor;

import com.example.campaign.flight_service.constants.Constants;
import com.example.campaign.flight_service.enums.AppJobType;
import com.example.campaign.flight_service.fw.entity.BatchJobEntity;
import com.example.campaign.flight_service.fw.jobs.BatchJob;
import com.example.campaign.flight_service.fw.jobs.context.BatchJobContext;
import com.example.campaign.flight_service.fw.jobs.impl.CampaignBatchJob;
import com.example.campaign.flight_service.fw.service.BatchJobService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cb-dhana
 */
@Service
public class BatchJobExecutor {

    private static final Logger LOGGER = Logger.getLogger(BatchJobExecutor.class.getName());

    private static final List<String> MESSAGES = new ArrayList<>();
    private static final String KAFKA_TOPIC = "campaign";
    private static final String GROUP_ID = "campaign-consumer-group";

    private BatchJobService batchJobService;

    @Autowired
    public BatchJobExecutor(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }

    @KafkaListener(topics = KAFKA_TOPIC, groupId = GROUP_ID)
    public void consume(String message) {
        try {
            JSONObject object = new JSONObject(message);
            Long batchJobId = object.getLong(Constants.Job.BATCH_JOB_ID);
            int batchJobType = object.getInt(Constants.Job.BATCH_JOB_TYPE);
            BatchJobEntity jobEntity = batchJobService.getJob(batchJobId).get();
            BatchJobContext batchJobContext = BatchJobContext.builder()
                    .context(new JSONObject(jobEntity.getContext()))
                    .jobId(batchJobId)
                    .batchJob(jobEntity)
                    .build();
            getBatchJob(batchJobType, batchJobContext, batchJobService).run();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "SEVERE:: Exception while consuming message", e);
        }
    }

    private BatchJob getBatchJob(int batchJobType, BatchJobContext batchJobContext, BatchJobService batchJobService) throws Exception {
        AppJobType appJobType = AppJobType.getJob(batchJobType);
        switch (appJobType) {
            case CAMPAIGN_JOB:
                return new CampaignBatchJob(batchJobContext, batchJobService);
            default:
                throw new Exception("Invalid Batch Job Type");
        }
    }

    public List<String> getMessages() {
        return MESSAGES;
    }

}
