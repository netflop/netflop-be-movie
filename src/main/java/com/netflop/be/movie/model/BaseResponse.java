package com.netflop.be.movie.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseResponse <T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
}
