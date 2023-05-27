package com.example.campaign.flight_service.constants;

/**
 * @author cb-dhana
 */
public class Constants {

    public static final String VALIDATOR_PACKAGE = "com.example.campaign.flight_service.campaign.validator.impl.";

    public static class Discounts {
        public static final String AE = "OFFER_20";
        public static final String FK = "OFFER_30";
        public static final String LR = "OFFER_25";
        public static final String DEFAULT = "";
    }

    public static final class Job {

        public static final String BATCH_JOB_ID = "batch_job_id";
        public static final String BATCH_JOB_TYPE = "batch_job_type";
    }

    public static final class CampaignJob {
        public static final int BATCH_LIMIT = 2;
    }

    public static final class JobContext {

        public static final String INPUT_FILE = "input";
        public static final String SUCCESS_FILE = "success";
        public static final String OFFSET = "offset";
        public static final String BATCH_LIMIT = "batch_limit";
        public static final String FAILURE_FILE = "failure_file";
        public static final String STATUS = "status";
    }

    public static final class File {

        public static final String CAMPAIGN_FILE = "campaign";
        public static final String SUCCESS = "success";
        public static final String CSV = ".csv";
        public static final String FAILURE = "failure";
    }

    public static final class API {
        public static final String PATH = "/api/v1/flights";
    }

    public static  final class Ticket {
        public static final String FIRST_NAME = "First_name";
        public static final String LAST_NAME = "Last_name";
        public static final String PNR = "PNR";
        public static final String FAIR_CLASS = "Fare_class";
        public static final String TRAVEL_DATE = "Travel_date";
        public static final String PAX = "Pax";
        public static final String TICKETING_DATE = "Ticketing_date";
        public static final String EMAIL = "Email";
        public static final String MOBILE_PHONE = "Mobile_p";
        public static final String BOOKED_CABIN = "Booked cabin";
        public static final String OFFER_CODE = "Offer Code";
        public static final String FAILURES = "Failures";

        public static final String[] HEADERS = new String[] {FIRST_NAME, LAST_NAME, PNR, FAIR_CLASS, TRAVEL_DATE, PAX, TICKETING_DATE, EMAIL, MOBILE_PHONE, BOOKED_CABIN, OFFER_CODE};
        public static final String[] SUCCESS_HEADERS = new String[] {OFFER_CODE};
        public static final String[] FAILURE_HEADERS = new String[] {FAILURES};
    }
}
