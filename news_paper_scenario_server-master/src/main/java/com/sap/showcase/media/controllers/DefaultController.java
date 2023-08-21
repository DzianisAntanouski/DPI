package com.sap.showcase.media.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sap.showcase.media.controllers.ControllerConst.API_ADVT_PATH;
import static com.sap.showcase.media.controllers.ControllerConst.API_CUST_PATH;
import static com.sap.showcase.media.controllers.ControllerConst.API_MGZN_PATH;
import static com.sap.showcase.media.controllers.ControllerConst.API_SUBT_PATH;

@RestController
public class DefaultController {

    @GetMapping
    @RequestMapping("/")
    public String getDefault() {
        return "ShowCaseApps Run.";
    }
    
    @GetMapping
    @RequestMapping("/api")
    public String getAPIDefault() {
    	return "Input API End Point for:\n"
        		+ "\t\t" +  API_ADVT_PATH + "\n"
        		+ "\t\t" +  API_CUST_PATH + "\n"
        		+ "\t\t" +  API_MGZN_PATH + "\n"
        		+ "\t\t" +  API_SUBT_PATH + "\n";
    }
    
}
