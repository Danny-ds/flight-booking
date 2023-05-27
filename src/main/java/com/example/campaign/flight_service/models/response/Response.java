package com.example.campaign.flight_service.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cb-dhana
 */
@NoArgsConstructor
@Setter
public class Response {

    @JsonProperty("code")
    protected int code;

    @JsonProperty("message")
    protected String message;
}
