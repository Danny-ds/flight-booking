package com.example.campaign.flight_service.campaign;

import com.example.campaign.flight_service.constants.Constants;

/**
 * @author cb-dhana
 */
public class FairClassFK extends FairClass {

    @Override
    public String getDiscountCode() {
        return Constants.Discounts.FK;
    }
}
