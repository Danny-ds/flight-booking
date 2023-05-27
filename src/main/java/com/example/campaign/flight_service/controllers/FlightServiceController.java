package com.example.campaign.flight_service.controllers;

import com.example.campaign.flight_service.constants.Constants;
import com.example.campaign.flight_service.models.FlightServiceMeta;
import com.example.campaign.flight_service.models.response.CampaignStatus;
import com.example.campaign.flight_service.models.response.PromotionalCampaign;
import com.example.campaign.flight_service.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author cb-dhana
 */
@RestController
@RequestMapping(path = Constants.API.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightServiceController {

    private FlightService flightService;

    public FlightServiceController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(path = "/services")
    public ResponseEntity<FlightServiceMeta> getFlightServices() {
        return new ResponseEntity<>(flightService.getFlightServiceMeta(), HttpStatus.OK);
    }

    @PostMapping(path = "/campaign")
    public ResponseEntity<PromotionalCampaign> processPromotionalCampaign(MultipartFile file) throws Exception {
        return new ResponseEntity<>(flightService.processPromotionalCampaign(file), HttpStatus.CREATED);
    }

    @GetMapping(path = "/campaign/status/{jobId}")
    public ResponseEntity<CampaignStatus> campaignStatus(@PathVariable Long jobId) throws Exception {
        return new ResponseEntity<>(flightService.handleCampaignStatus(jobId), HttpStatus.OK);
    }
}
