package com.example.campaign.flight_service.campaign.validator;

import com.example.campaign.flight_service.campaign.TicketRecord;
import com.example.campaign.flight_service.constants.Constants;
import org.apache.commons.csv.CSVRecord;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cb-dhana
 */
public abstract class Validator {

    public boolean isValid = true;
    public List<String> failureMessages;

    public abstract void validate(TicketRecord ticketRecord) throws Exception;

    public static List<String> validateRecord(TicketRecord ticketRecord) throws Exception {
        List<String> failureMessage = new ArrayList<>();
        for (Field field : ticketRecord.getClass().getDeclaredFields()) {
            String classRef = field.getName()+"Validator";
            classRef = Character.toUpperCase(classRef.charAt(0)) + classRef.substring(1);
            classRef = Constants.VALIDATOR_PACKAGE + classRef;
            Class<Validator> validatorClass = (Class<Validator>) Class.forName(classRef);
            Validator validator = validatorClass.getDeclaredConstructor().newInstance();
            validator.validate(ticketRecord);

            if (!validator.isValid) {
                failureMessage.addAll(validator.failureMessages);
            }
        }
        return failureMessage;
    }

    public static TicketRecord getTicketRecord(CSVRecord record) {
        return TicketRecord.builder()
                .firstName(record.get(Constants.Ticket.FIRST_NAME))
                .lastName(record.get(Constants.Ticket.LAST_NAME))
                .pnr(record.get(Constants.Ticket.PNR))
                .fairClass(record.get(Constants.Ticket.FAIR_CLASS))
                .travelDate(record.get(Constants.Ticket.TRAVEL_DATE))
                .pax(Integer.parseInt(record.get(Constants.Ticket.PAX)))
                .ticketingDate(record.get(Constants.Ticket.TICKETING_DATE))
                .email(record.get(Constants.Ticket.EMAIL))
                .mobilePhone(record.get(Constants.Ticket.MOBILE_PHONE))
                .bookedCabin(record.get(Constants.Ticket.BOOKED_CABIN))
                .build();
    }

    protected void addFailureMessages(String messages) {
        if (failureMessages == null) {
            failureMessages = new ArrayList<>();
        }
        failureMessages.add(messages);
    }
}
