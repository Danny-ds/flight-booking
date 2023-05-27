package com.example.campaign.flight_service.campaign.validator.impl;

import com.example.campaign.flight_service.campaign.TicketRecord;
import com.example.campaign.flight_service.campaign.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @author cb-dhana
 */
public class BookedCabinValidator extends Validator {

    @Override
    public void validate(TicketRecord ticketRecord) throws Exception {
        String cabin = ticketRecord.getBookedCabin();
        Pattern pattern = Pattern.compile("^(Economy|Premium Economy|Business|First)$");
        if (StringUtils.isEmpty(cabin) || !pattern.matcher(cabin).matches()) {
            isValid = false;
            addFailureMessages("Invalid Booked Cabin");
        }
    }
}
