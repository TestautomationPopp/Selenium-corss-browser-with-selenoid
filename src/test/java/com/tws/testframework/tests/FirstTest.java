/**
 * #################### Test With A Smile ####################
 *                  Written by Andreas Popp
 *  For more informations visit https://test-with-a-smile.de
 *        or mailto andreas.popp@testautomation-popp.de
 * ###########################################################
 */

package com.tws.testframework.tests;

import com.tws.testframework.framework.Browser;
import com.tws.testframework.dataprovider.SearchProvider;

import com.tws.testframework.keywords.*;

import org.openqa.selenium.WebDriver;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

import org.testng.ITestResult;

import java.util.HashMap;



public class FirstTest{

    // Create a public variable for the browser class to call the driver
    HashMap<Integer, WebDriver> driverMap = new HashMap<Integer, WebDriver>();

    private WebDriver initializeBrowser(String testNo, String browsername) throws Exception{
        com.tws.testframework.framework.Browser browser = new Browser(browsername, 10);

        driverMap.put(Integer.parseInt(testNo), browser.driver);

        return browser.driver;
    }

    /**
     * The test case opens the website https://test-with-a-smile.de and verify, that the logo is displayed
     * 
     * @param browsername   The browser defined in the data provider given from the -Dbrowsers parameter of the maven call
     * @throws Exception
     */
    @Test(dataProvider = "search-data-provider", dataProviderClass = SearchProvider.class)
    private void testCase(String testNo, String browsername, String searchTerm, String searchResultString, String firstResultHeader, String firstResultSummary) throws Exception{
        
        WebDriver driver = initializeBrowser(testNo, browsername);

        OpenPage.openTwsMain(driver);

        Search.searchForTerm(driver, searchTerm);

        Search.checkFirstSearchResult(driver, searchResultString, firstResultHeader, firstResultSummary);
        
        Thread.sleep(10000);

    }
    
    /**
     * The after method will close the browser when the execution of the test method is done.
     */
    @AfterMethod
    private void closeBrowsers(ITestResult result){
        WebDriver driver = driverMap.get(Integer.parseInt(result.getParameters()[0].toString()));
        driver.quit();
    }
}