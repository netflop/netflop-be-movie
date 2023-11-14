package com.netflop.be.movie.model;

import lombok.Data;

@Data
public class MovieResponse {
    private String id;
    private String name;
    private String description;
    private String movieUrl;
    private String category;
    private int view;
    private int like;
    private int dislike;
    private String trailerUrl;
}
