package com.accion.model;

import lombok.Data;

@Data
public class CvssMetricV31 {
    private String source;
    private String type;
    private CvssData cvssData;
    private double exploitabilityScore;
    private double impactScore;

    // Getters and Setters
}

