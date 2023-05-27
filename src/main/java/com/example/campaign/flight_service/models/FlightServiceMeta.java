package com.example.campaign.flight_service.models;

import com.example.campaign.flight_service.models.response.Response;
import lombok.Builder;

import java.util.List;

/**
 * @author cb-dhana
 */
@Builder
public class FlightServiceMeta extends Response {
    public String flightName;
    public List<String> services;
}
