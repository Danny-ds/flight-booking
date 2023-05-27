package com.example.campaign.flight_service.campaign.validator.impl;

import com.example.campaign.flight_service.campaign.TicketRecord;
import com.example.campaign.flight_service.campaign.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @author cb-dhana
 */
public class MobilePhoneValidator extends Validator {

    @Override
    public void validate(TicketRecord ticketRecord) throws Exception {
        String mobile = ticketRecord.getMobilePhone();
        Pattern pattern = Pattern.compile("^[9876][0-9]{9}$");
        if (StringUtils.isEmpty(mobile) || mobile.length() != 10 || !pattern.matcher(mobile).matches()) {
            isValid = false;
            addFailureMessages("Mobile Number Invalid");
        }
    }
}
