package com.mn.environment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FiServiceController {

    private FiServiceConfiguration fiServiceConfiguration;

    public FiServiceController(FiServiceConfiguration fiServiceConfiguration) {
        this.fiServiceConfiguration = fiServiceConfiguration;
    }

    @GetMapping("/fi-service")
    public FiServiceConfiguration getFiServiceConfiguration() {
        return fiServiceConfiguration;
    }
}
