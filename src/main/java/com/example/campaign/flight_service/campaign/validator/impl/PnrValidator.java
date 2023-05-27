package com.example.campaign.flight_service.campaign.validator.impl;

import com.example.campaign.flight_service.campaign.TicketRecord;
import com.example.campaign.flight_service.campaign.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @author cb-dhana
 */
public class PnrValidator extends Validator {

    @Override
    public void validate(TicketRecord ticketRecord) throws Exception {
        String pnr = ticketRecord.getPnr();
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{6}$");
        if (StringUtils.isEmpty(pnr) || !pattern.matcher(pnr).matches() ) {
            isValid = false;
            addFailureMessages("Invalid PNR Number");
        }
    }
}
