package com.example.campaign.flight_service.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

/**
 * @author cb-dhana
 */
@Setter
public class CampaignStatus extends Response {

    @JsonProperty("success_file_location")
    private String successFilePath;

    @JsonProperty("failure_file_location")
    private String failureFilePath;
}
