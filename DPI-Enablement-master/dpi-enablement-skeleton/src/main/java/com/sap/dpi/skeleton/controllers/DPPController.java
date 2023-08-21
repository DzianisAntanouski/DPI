package com.sap.dpi.skeleton.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DPPController {

    @Value("classpath:config/information-metadata.json")
    private Resource metadata;

    @GetMapping("/dpp/metadata")
    public Map<?, ?> metadata() throws IOException {
        return new ObjectMapper().readValue(metadata.getFile(), Map.class);
    }

}
