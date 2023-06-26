package com.example.cucumberrest.service;

import com.example.cucumberrest.model.TestRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class TestExecutionService {

    public String executeTests(TestRequest testRequest) {
        try {
            System.out.println("calling TestExecutionService.executeTests()");
            //String command = createMvnTestCommand(testRequest);

            // If running on Windows, use `cmd.exe /c mvn test` command
            String command = "cmd.exe /c mvn test"; // Windows
            // If running on Linux, use `mvn test` command
            // Execute the `mvn test` command programmatically using ProcessBuilder
            Process process = Runtime.getRuntime().exec(command);

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish and get the exit code
            int exitCode = process.waitFor();

            // Handle the test execution results based on the exit code
            if (exitCode == 0) {
                System.out.println("Test execution completed successfully");
                System.out.println("Printing output ********************************************\n");
                System.out.println(output.toString());
                System.out.println("End of output ***********************************************\n");
                // Tests passed
                return "Tests executed successfully";
            } else {
                // Tests failed or encountered errors
                String errorMessage = "Test execution failed. Exit code: " + exitCode + "\n";
                errorMessage += "Output:\n" + output.toString();
                System.out.println(errorMessage);
                return "Test execution failed. Please check the logs for more details";
            }
        } catch (IOException | InterruptedException e) {
            // Handle exceptions if the command execution fails
            System.out.println("Test execution failed due to an exception: " + e);
            return "Test execution failed due to an exception , Please check the logs for more details ";
        }
    }

    private String createMvnTestCommand(TestRequest testRequest) {
        String mvnTestCommand = "cmd.exe /c mvn test "; // Windows
        String cucumberOptions = "\"-Dcucumber.options=--tags ?\"";
        String executionEnvironment = "\"-DexecutionEnvironment=sit\"";
        String options = "'@" + testRequest.getTestType() + " and @" + testRequest.getServiceType()+ "'";
        cucumberOptions = cucumberOptions.replace("?", options);
        mvnTestCommand += cucumberOptions + " " + executionEnvironment;
        return mvnTestCommand;
    }

    public void validateRequest(TestRequest testRequest) {

        if (testRequest == null) {
            throw new IllegalArgumentException("Test request cannot be null");
        }
        if (testRequest.getTestType() == null || testRequest.getTestType().isEmpty()) {
            throw new IllegalArgumentException("Test type cannot be empty");
        }
        if (testRequest.getServiceType() == null || testRequest.getServiceType().isEmpty()) {
            throw new IllegalArgumentException("Service type cannot be empty");
        }
    }

    @Async
    public DeferredResult<String> executeTestAsync(TestRequest testRequest) {

        writeTestPropertiesToFile(testRequest);

        DeferredResult<String> deferredResult = new DeferredResult<>();

        // Asynchronous method using CompletableFuture
        CompletableFuture.supplyAsync(() -> executeTests(testRequest)).whenCompleteAsync((result, throwable) -> {
            if (throwable != null) {
                deferredResult.setErrorResult("An error occurred while executing mvn test command");
            } else {
                // Set the final result
                System.out.println("final result : " + result);
                deferredResult.setResult(result);

            }
        });

        return deferredResult;
    }

    private void writeTestPropertiesToFile(TestRequest testRequest) {
        Properties properties = new Properties();
        String path = "src/test/resources/";
        String fileName = "test.properties";

        properties.setProperty("testType", testRequest.getTestType());
        properties.setProperty("serviceType", testRequest.getServiceType());

        // Save the properties to a .properties file
        try (FileOutputStream outputStream = new FileOutputStream(path + fileName)) {
            properties.store(outputStream, "Request Properties");
            System.out.println("Test properties saved to file " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving request properties to file: " + e);
        }

    }

    @Async
    public String asyncTest(TestRequest testRequest) throws ExecutionException, InterruptedException {

        System.out.println("calling TestService.asyncTest()");

        // Asynchronous method using CompletableFuture
        CompletableFuture<String> future = new CompletableFuture<>();

        // Set an initial response
        future.complete("Test execution started successfully, We will update test results when it is completed...");

        // Perform the time-consuming operation asynchronously
        CompletableFuture.runAsync(() -> {
            String result = executeTests(testRequest);

            // Set the final result
            future.complete(result);
        });

        return future.get();
    }
}
