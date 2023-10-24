package com.netflop.be.movie.controller;

import com.netflop.be.movie.entity.Movie;
import com.netflop.be.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public String getMovieById(@RequestParam String id) {
        return id;
    }
}
