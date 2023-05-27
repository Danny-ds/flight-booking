package com.example.campaign.flight_service.enums;

/**
 * @author cb-dhana
 */
public enum JobStatus {
    SCHEDULED(10), STARTED(20), IN_PROGRESS(25), FAILED(30), RESCHEDULED(40), SERVER_QUIT(50), COMPLETED(60);

    private int code;

    JobStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
