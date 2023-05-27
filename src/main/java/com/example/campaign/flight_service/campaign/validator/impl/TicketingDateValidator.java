package com.example.campaign.flight_service.campaign.validator.impl;

import com.example.campaign.flight_service.campaign.TicketRecord;
import com.example.campaign.flight_service.campaign.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cb-dhana
 */
public class TicketingDateValidator extends Validator {

    @Override
    public void validate(TicketRecord ticketRecord) throws Exception {
        String ticketDate = ticketRecord.getTicketingDate();
        if (StringUtils.isEmpty(ticketDate)) {
            isValid = false;
            addFailureMessages("Ticket Date is Empty");
            return;
        }
        if (!isBeforeTravelDate(ticketDate, ticketRecord)) {
            isValid = false;
            addFailureMessages("Ticket Date is not before Travel Date");
        }
    }

    private boolean isBeforeTravelDate(String ticketDate, TicketRecord ticketRecord) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date ticDate = dateFormat.parse(ticketDate);
        Date travelDate = dateFormat.parse(ticketRecord.getTravelDate());
        return ticDate.before(travelDate);
    }
}
