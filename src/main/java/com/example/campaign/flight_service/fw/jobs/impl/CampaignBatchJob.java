package com.example.campaign.flight_service.fw.jobs.impl;

import com.example.campaign.flight_service.campaign.FairClass;
import com.example.campaign.flight_service.campaign.TicketRecord;
import com.example.campaign.flight_service.campaign.validator.Validator;
import com.example.campaign.flight_service.constants.Constants;
import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.jobs.BatchJob;
import com.example.campaign.flight_service.fw.jobs.context.BatchJobContext;
import com.example.campaign.flight_service.fw.service.BatchJobService;
import com.example.campaign.flight_service.fw.utils.FileUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author cb-dhana
 */
public class CampaignBatchJob extends BatchJob {

    private static final Logger LOGGER = Logger.getLogger(CampaignBatchJob.class.getName());

    private int offset;
    private int limit;
    private String inputFile;
    private String successFile;
    private String failureFile;
    private CSVPrinter successPrinter;
    private CSVPrinter failurePrinter;

    public CampaignBatchJob(BatchJobContext jobContext, BatchJobService batchJobService) {
        super(jobContext, batchJobService);
    }

    @Override
    protected void init() throws Exception {
        offset = jobContext.propInt(Constants.JobContext.OFFSET);
        limit = jobContext.propInt(Constants.JobContext.BATCH_LIMIT);
        inputFile = jobContext.prop(Constants.JobContext.INPUT_FILE);
        successFile = jobContext.prop(Constants.JobContext.SUCCESS_FILE);
        failureFile = jobContext.prop(Constants.JobContext.FAILURE_FILE);
    }

    @Override
    protected void execute() throws Exception {
        initPrinters();
        try {
            List<CSVRecord> records = getRecords();
            for (CSVRecord record : records) {
                try {
                    TicketRecord ticketRecord = Validator.getTicketRecord(record);
                    List<String> failureMessage = Validator.validateRecord(ticketRecord);

                    if (failureMessage.isEmpty()) {
                        String discountCode = FairClass.getFairClass(ticketRecord.getFairClass().charAt(0)).getDiscountCode();
                        addSuccessRecord(record, discountCode);
                    } else {
                        addFailureRecord(record, failureMessage);
                    }

                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "SEVER::Exception Occurred", e);
                    addFailureRecord(record, Collections.singletonList(e.getMessage()));
                }
            }
            batchJobService.updateBatchJobStatus(jobContext.getJobEntity(), JobStatus.COMPLETED);
        } finally {
            successPrinter.close();
            failurePrinter.close();
        }
    }

    private List<CSVRecord> getRecords() throws Exception {
        File file = new File(inputFile);
        CSVFormat csvFormat = CSVFormat.RFC4180.builder().setHeader(Constants.Ticket.HEADERS).setSkipHeaderRecord(true).build();

        try (CSVParser csvParser = new CSVParser(new FileReader(file), csvFormat)) {
            return csvParser.stream().skip(offset).limit(limit).collect(Collectors.toList());
        }
    }

    public void initPrinters() throws Exception {
        if (Objects.isNull(successPrinter)) {
            String[] headers = getHeaders(Constants.Ticket.HEADERS, Constants.Ticket.SUCCESS_HEADERS);
            CSVFormat csvFormat = CSVFormat.RFC4180.builder().setHeader(headers).setSkipHeaderRecord(true).build();
            successPrinter = new CSVPrinter(new FileWriter(successFile, true), csvFormat);
        }
        if (Objects.isNull(failurePrinter)) {
            String[] headers = getHeaders(Constants.Ticket.HEADERS, Constants.Ticket.SUCCESS_HEADERS);
            CSVFormat csvFormat = CSVFormat.RFC4180.builder().setHeader(headers).setSkipHeaderRecord(true).build();
            failurePrinter = new CSVPrinter(new FileWriter(failureFile, true), csvFormat);
        }
    }

    private String[] getHeaders(String[] headers, String[] additionalHeaders) {
        String[] result = Arrays.copyOf(headers, headers.length + additionalHeaders.length);
        for (int i=headers.length; i< result.length; i++) {
            result[i] = additionalHeaders[i-headers.length];
        }
        return result;
    }

    public void addSuccessRecord(CSVRecord record, String discountCode) throws Exception {
        for (final Object value : record) {
            successPrinter.print(value);
        }
        successPrinter.print(discountCode);
        successPrinter.println();
    }

    private void addFailureRecord(CSVRecord record, List<String> message) throws Exception {
        for (final Object value : record) {
            failurePrinter.print(value);
        }
        failurePrinter.print(message);
        failurePrinter.println();
    }
}
