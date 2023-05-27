package com.example.campaign.flight_service.campaign.validator.impl;

import com.example.campaign.flight_service.campaign.TicketRecord;
import com.example.campaign.flight_service.campaign.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cb-dhana
 */
public class EmailValidator extends Validator {

    @Override
    public void validate(TicketRecord ticketRecord) throws Exception {
        String email = ticketRecord.getEmail();
        Pattern pattern = Pattern.compile("^[a-zA-Z_]+@\\w+\\.com$");

        if (StringUtils.isEmpty(email) || !pattern.matcher(email).matches()) {
            isValid = false;
            addFailureMessages("EmailId Invalid");
        }
    }
}
