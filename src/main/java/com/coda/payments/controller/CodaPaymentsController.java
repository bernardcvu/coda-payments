package com.coda.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class CodaPaymentsController {

    private final Logger logger = LoggerFactory.getLogger(CodaPaymentsController.class);

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/response", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postResponse(@RequestBody String request) {
        logger.info("Executing request on port {} : {}", port, request);
        if(isRequestValidJson(request)) {
            return new ResponseEntity<>(request, HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"message\":\"Invalid request format\"}", HttpStatus.BAD_REQUEST);
    }

    private boolean isRequestValidJson(String jsonString ) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @GetMapping("/")
    public String home() {
        logger.info("Accessing port {}", port);
        return "Instance " + port.charAt(3);
    }
}
