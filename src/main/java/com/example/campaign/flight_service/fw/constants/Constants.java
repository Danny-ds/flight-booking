package com.example.campaign.flight_service.fw.constants;

/**
 * @author cb-dhana
 */
public class Constants {

    public static final class Table {
        public static final String JOBS = "jobs";
        public static final String BATCH_JOBS = "batch_jobs";

        public static class Jobs {

            public static final String ID = "id";
            public static final String CONTEXT = "context";
            public static final String STATUS = "status";
            public static final String CREATED_AT = "created_at";
            public static final String UPDATED_AT = "updated_at";
            public static final String SCHEDULED_AT = "scheduled_at";
            public static final String JOB_TYPE = "job_type";
        }

        public static final class BatchJobs extends Jobs {
            public static final String JOB_ID = "job_id";
        }
    }

    public static final class File {
        public static final String INPUT_DIR = "src/main/resources/input/";
        public static final String OUTPUT_DIR = "src/main/resources/output/";
    }
}
