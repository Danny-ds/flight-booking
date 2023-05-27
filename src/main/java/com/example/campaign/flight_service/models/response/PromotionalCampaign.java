package com.example.campaign.flight_service.models.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

/**
 * @author cb-dhana
 */
@Setter
public class PromotionalCampaign extends Response {

    @JsonProperty("id")
    private Long id;
}
