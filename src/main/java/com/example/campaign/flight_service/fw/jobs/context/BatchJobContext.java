

package com.example.campaign.flight_service.fw.jobs.context;


import com.example.campaign.flight_service.fw.entity.BatchJobEntity;
import lombok.Builder;
import org.json.JSONObject;

/**
 * @author cb-dhana
 */
@Builder
public class BatchJobContext {

    private Long jobId;
    private JSONObject context;
    private BatchJobEntity batchJob;

    public Object prop(String name, Object value) {
        return context.put(name, value);
    }

    public String prop(String name) {
        return context.getString(name);
    }

    public int propInt(String name) {
        return context.getInt(name);
    }

    public JSONObject getContext() {
        return context;
    }

    public Long getJobId() {
        return jobId;
    }

    public BatchJobEntity getJobEntity() {
        return batchJob;
    }
}
