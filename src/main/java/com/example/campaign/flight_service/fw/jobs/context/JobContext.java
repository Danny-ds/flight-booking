package com.example.campaign.flight_service.fw.jobs.context;


import com.example.campaign.flight_service.fw.entity.JobEntity;
import lombok.Builder;
import org.json.JSONObject;

/**
 * @author cb-dhana
 */
@Builder
public class JobContext {

    private Long jobId;
    private JSONObject context;
    private JobEntity jobEntity;

    public Object prop(String name, Object value) {
        context.put(name, value);
        jobEntity.setContext(context.toString());
        return context;
    }

    public String prop(String name) {
        return context.getString(name);
    }

    public int optInt(String name, int defaultValue) {
        return context.optInt(name);
    }

    public JSONObject getContext() {
        return context;
    }

    public Long getJobId() {
        return jobId;
    }

    public JobEntity getJobEntity() {
        return jobEntity;
    }
}
