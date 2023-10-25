package com.myorg.dto;

import com.myorg.model.Api;
import com.myorg.model.Cognito;
import com.myorg.model.Lambda;
import lombok.Data;

import java.util.Map;

@Data
public class ConfigDTO {
    private Lambda lambda;
    private Cognito cognito;
    private Api api;
}


