package com.example.cucumberrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestConfig {
    private String authBaseURL;
    private String loginUser;
}
