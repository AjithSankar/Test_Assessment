package com.example.cucumberrest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestRequest {
    private String testType;
    private String serviceType;
    private TestConfig testConfig;
}
