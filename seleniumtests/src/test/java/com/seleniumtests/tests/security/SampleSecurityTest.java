package com.seleniumtests.tests.security;

import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ClientApiException;

import com.seleniumtests.blogexamples.driversetup.BaseSecurity;

import lombok.extern.slf4j.Slf4j;


@Slf4j


public class SampleSecurityTest extends BaseSecurity {

	
    public static String JUICE_SHOP = "http://192.168.81.224:8089/petclinic/";
    
    

    @Test()
    public void spiderHomePage() throws ClientApiException, InterruptedException {
        getDriver().get(JUICE_SHOP);
        spiderTarget(JUICE_SHOP);
    }

    @Test()
    public void passiveScanHomePage() throws ClientApiException {
        getDriver().get(JUICE_SHOP);
        // some more logic using page object to move to different pages goes here
        waitForPassiveScanToComplete();
        checkRiskCount(JUICE_SHOP);
    }

    @Test()
    public void activeScanHomePage() throws ClientApiException, InterruptedException {
        getDriver().get(JUICE_SHOP);
        activeScan(JUICE_SHOP);
        checkRiskCount(JUICE_SHOP);
    }
    
}


