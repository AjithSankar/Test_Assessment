package com.example.cucumberrest.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class TestExecutionService {

    public String executeTests() {
        try {
            System.out.println("calling TestExecutionService.executeTests()");
            // If running on Windows, use `cmd.exe /c mvn test` command
            // If running on Linux, use `mvn test` command
            String cmd = "cmd.exe /c mvn test";  // Windows
            // Execute the `mvn test` command programmatically using ProcessBuilder
            System.out.println("executing command: " + cmd);
            Process process = Runtime.getRuntime().exec(cmd);

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
}
