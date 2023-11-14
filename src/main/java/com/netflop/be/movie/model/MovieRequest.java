package com.netflop.be.movie.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieRequest {
    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "movieUrl is mandatory")
    private String movieUrl;

    private String description;
    private String category;
}
