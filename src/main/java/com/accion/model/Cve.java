package com.accion.model;

import lombok.Data;

import java.util.List;

@Data
public class Cve {
    private String id;
    private String sourceIdentifier;
    private String published;
    private String lastModified;
    private String vulnStatus;
    private List<Description> descriptions;
    private Metrics metrics;
    private List<Weakness> weaknesses;
    private List<Reference> references;

    // Getters and Setters
}

