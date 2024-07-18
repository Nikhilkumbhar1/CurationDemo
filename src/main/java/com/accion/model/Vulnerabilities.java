package com.accion.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Vulnerabilities {

    @JsonProperty("package")
    private Package packageName;

    @JsonProperty("vulnerable_version_range")
    private String vulnerableVersionRange;

    @JsonProperty("first_patched_version")
    private String firstPatchedVersion;




}
