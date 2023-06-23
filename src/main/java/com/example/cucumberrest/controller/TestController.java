package com.example.cucumberrest.controller;


import com.example.cucumberrest.model.TestRequest;
import com.example.cucumberrest.service.TestExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

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

    @PostMapping("/run/async")
    public String executeTestAsync(@RequestBody TestRequest testRequest) throws InterruptedException {
        System.out.println("calling TestController.executeTests()");

        DeferredResult<String> deferredResult = new DeferredResult<>();

        // Set an initial response
        deferredResult.setResult("Test execution started successfully, We will update test results when it is completed..");
        testExecutionService.validateRequest(testRequest);
        testExecutionService.executeTestAsync(testRequest);

        return (String) deferredResult.getResult();
    }

    @GetMapping("/async")
    public String asyncTest() {

        System.out.println("calling TestController.asyncTest()");

        String initialResponse = "Test execution is in progress, please wait...";

        //DeferredResult<String> stringDeferredResult = testExecutionService.asyncTest();
       // System.out.println("stringDeferredResult: " + stringDeferredResult);

        return initialResponse;
    }
}
