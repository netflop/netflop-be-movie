package com.myorg.model;

import lombok.Data;

import java.util.Map;

@Data
public class Lambda {
    private String name;
    private String handler;
    private String codePath;
    private Map<String, String> env;
}
