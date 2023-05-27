package com.example.campaign.flight_service.campaign.validator.impl;

import com.example.campaign.flight_service.campaign.TicketRecord;
import com.example.campaign.flight_service.campaign.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @author cb-dhana
 */
public class FairClassValidator extends Validator {

    @Override
    public void validate(TicketRecord ticketRecord) throws Exception {
        String fairClass = ticketRecord.getFairClass();
        Pattern pattern = Pattern.compile("^[A-Z]$");
        if (StringUtils.isEmpty(fairClass) || fairClass.length() > 1 || !pattern.matcher(fairClass).matches()) {
            isValid = false;
            addFailureMessages("Invalid FairClass");
        }
    }
}
