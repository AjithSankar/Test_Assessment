package com.example.cucumberrest.controller;


import com.example.cucumberrest.model.TestRequest;
import com.example.cucumberrest.service.TestExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestExecutionService testExecutionService;

    @PostMapping("/run")
    public String executeTests(@RequestBody TestRequest testRequest) {
        System.out.println("calling TestController.executeTests()");

        testExecutionService.validateRequest(testRequest);

        return testExecutionService.executeTests(testRequest);
    }
}
