package com.accion.model;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    private int resultsPerPage;
    private int startIndex;
    private int totalResults;
    private String format;
    private String version;
    private String timestamp;
    private List<Vulnerability> vulnerabilities;

    // Getters and Setters
}

