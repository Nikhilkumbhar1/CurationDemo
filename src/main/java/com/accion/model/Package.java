package com.accion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Package {

    @JsonProperty("ecosystem")
    private String packageType;

    private String name;


}
