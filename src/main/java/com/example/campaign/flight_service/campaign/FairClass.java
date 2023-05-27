package com.example.campaign.flight_service.campaign;

import com.example.campaign.flight_service.constants.Constants;

/**
 * @author cb-dhana
 */
public class FairClass {


    public String getDiscountCode() {
        return Constants.Discounts.DEFAULT;
    }

    public static FairClass getFairClass(Character fairClass) throws Exception {
        if (fairClass >= 'A' && fairClass <= 'E') {
            return new FairClassAE();
        }
        if (fairClass >= 'F' && fairClass <= 'K') {
            return new FairClassAE();
        }
        if (fairClass >= 'L' && fairClass <= 'R') {
            return new FairClassAE();
        }
        if (Character.isLetter(fairClass)) {
            return new FairClass();
        }
        throw new Exception("Invalid Fair Class: " + fairClass);
    }
}
