package com.example.campaign.flight_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cb-dhana
 */
@RestController
public class IndexController {

    @GetMapping(path = "/")
    public String getIndex() {
        return "index";
    }
}
