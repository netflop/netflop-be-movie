package com.netflop.be.movie.service;

import com.netflop.be.movie.common.IAuthenticationFacade;
import com.netflop.be.movie.dao.MovieDao;
import com.netflop.be.movie.common.CustomUserDetail;
import com.netflop.be.movie.entity.Movie;
import com.netflop.be.movie.exception.ItemNotFoundException;
import com.netflop.be.movie.model.MovieRequest;
import com.netflop.be.movie.model.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService{
    private final MovieDao movieDao;
    private final IAuthenticationFacade authenticationFacade;
    private final ModelMapper modelMapper;

    public MovieResponse getMovieById(String id) {
        CustomUserDetail currentUser = (CustomUserDetail) authenticationFacade.getAuthentication().getPrincipal();
        var movie = movieDao.findById(id);

        if (movie == null) {
            throw new ItemNotFoundException("The movie not found");
        }

        if(currentUser.getUserType().equals("User") && movie.isDeleted()) {
            return null;
        }

        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    public boolean deleteMovie(String id, boolean isSortDelete) {
        CustomUserDetail currentUser = (CustomUserDetail) authenticationFacade.getAuthentication().getPrincipal();
        var movie = movieDao.findById(id);

        if (movie == null) {
            throw new ItemNotFoundException("The movie not found");
        }

        if (isSortDelete) {
            movie.setDeleted(true);
            movie.setUpdatedBy(currentUser.getUserId());
            movie.setUpdatedAt(Instant.now());

            movieDao.update(movie);
        } else {
            movieDao.delete(id);
        }

        return true;
    }

    @Override
    public List<MovieResponse> getAllMovies(String id) {
        return null;
    }

    public MovieResponse insertMovie(MovieRequest request) {
        CustomUserDetail currentUser = (CustomUserDetail) authenticationFacade.getAuthentication().getPrincipal();

        var movie = new Movie();
        movie.setId(UUID.randomUUID().toString());

        movie.setName(request.getName());
        movie.setDescription(request.getDescription());
        movie.setMovieUrl(request.getMovieUrl());
        movie.setCategory(request.getCategory());
        movie.setCreatedAt(Instant.now());
        movie.setUpdatedAt(Instant.now());
        movie.setCreatedBy(currentUser.getUserId());
        movie.setUpdatedBy(currentUser.getUserId());

        movieDao.insert(movie);

        return modelMapper.map(movie, MovieResponse.class);
    }

    public MovieResponse updateMovie(String id, MovieRequest request) {
        CustomUserDetail currentUser = (CustomUserDetail) authenticationFacade.getAuthentication().getPrincipal();

        var movie = movieDao.findById(id);

        if (movie == null) {
            throw new ItemNotFoundException("The movie not found");
        }

        movie.setName(request.getName());
        movie.setDescription(request.getDescription());
        movie.setMovieUrl(request.getMovieUrl());
        movie.setCategory(request.getCategory());
        movie.setUpdatedAt(Instant.now());
        movie.setUpdatedBy(currentUser.getUserId());

        return modelMapper.map(movieDao.update(movie), MovieResponse.class);
    }

    public List<MovieResponse> getMovies(String keyWord, String category, int offset, int limit) {
        var movies = movieDao.find(keyWord, category, offset, limit);

        return movies.stream().map(ele -> modelMapper.map(ele, MovieResponse.class)).toList();
    }
}
