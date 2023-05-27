package com.example.campaign.flight_service.campaign;

import lombok.Builder;
import lombok.Getter;

/**
 * @author cb-dhana
 */
@Builder
@Getter
public class TicketRecord {
    private String firstName;
    private String lastName;
    private String pnr;
    private String fairClass;
    private String travelDate;
    private int pax;
    private String ticketingDate;
    private String email;
    private String mobilePhone;
    private String bookedCabin;
}
