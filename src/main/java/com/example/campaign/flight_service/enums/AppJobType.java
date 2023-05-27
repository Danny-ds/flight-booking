package com.example.campaign.flight_service.enums;

import java.util.Arrays;

/**
 * @author cb-dhana
 */
public enum AppJobType {
    CAMPAIGN_JOB(1);

    public int code;

    AppJobType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AppJobType getJob(int code) {
        return Arrays.stream(AppJobType.values()).filter(jobType -> code == jobType.code).findFirst().get();
    }
}
