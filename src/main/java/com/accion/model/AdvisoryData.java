package com.accion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdvisoryData {

    @JsonProperty("ghsa_id")
    private String ghsaId;

    @JsonProperty("cve_id")
    private String cveId;

    @JsonProperty("summary")
    private String title;

    private List<Vulnerabilities> vulnerabilities;

    //@JsonProperty("vulnerable_version_range")
    //private List<String> affectedVersions;

    //@JsonProperty("first_patched_version")
    //private List<String> patchedVersions;

    private String severity;

    @JsonProperty("cvss")
    private Map<String, String> severityScore;

    private String description;

    private List<String> references;


}
