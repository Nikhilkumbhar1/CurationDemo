package com.accion.model;

import lombok.Data;

import java.util.List;

@Data
public class Weakness {
    private String source;
    private String type;
    private List<WeaknessDescription> description;

    // Getters and Setters
}

