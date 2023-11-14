package com.netflop.be.movie.service;

import com.netflop.be.movie.entity.Movie;
import com.netflop.be.movie.model.MovieRequest;
import com.netflop.be.movie.model.MovieResponse;

import java.util.List;

public interface IMovieService {
    MovieResponse getMovieById (String id);
    List<MovieResponse> getAllMovies (String id);
    MovieResponse insertMovie (MovieRequest request);
    MovieResponse updateMovie (String id, MovieRequest request);
    boolean deleteMovie(String id, boolean isSortDelete);
    List<MovieResponse> getMovies(String key, String category, int offset, int limit);
}
