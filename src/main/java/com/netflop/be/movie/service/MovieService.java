package com.netflop.be.movie.service;

import com.netflop.be.movie.dao.MovieDao;
import com.netflop.be.movie.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieDao movieDao;

    public Movie getMovieById(String id) {
        return movieDao.findById(id);
    }
}
