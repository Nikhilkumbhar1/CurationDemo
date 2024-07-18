package com.accion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CvssData {
    private String version;
    private String vectorString;
    private String attackVector;
    private String attackComplexity;
    private String privilegesRequired;
    private String userInteraction;
    private String scope;
    private String confidentialityImpact;
    private String integrityImpact;
    private String availabilityImpact;
    private double baseScore;
    private String baseSeverity;

    // Getters and Setters
}
