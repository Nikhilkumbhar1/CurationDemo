package com.accion.model;

import lombok.Data;

import java.util.List;

@Data
public class CurationData {

    private String ghsaId;
    private String cveId;
    private String title;
    private String language;
    private String packageName;
    private List<String> vulnerableVersions;
    private List<String> fixedVersions;
    private String description;
    private List<String> fixedCommit;
    private String cvssVector;
    private String cvss3Score;
    private String introVersion;
    private String introCommit;

}
