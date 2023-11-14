package com.netflop.be.movie.controller;

import com.netflop.be.movie.model.BaseResponse;
import com.netflop.be.movie.model.MovieRequest;
import com.netflop.be.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getMovieById(@PathVariable("id") String id) {
        var response = this.movieService.getMovieById(id);
        return ResponseEntity.ok(BaseResponse.builder()
                              .data(response)
                              .build());
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getMovies(@RequestParam("key") String key, @RequestParam("offset") int offset,
                                                     @RequestParam("limit") int limit, @RequestParam("category") String category) {
        var response = this.movieService.getMovies(key, category, offset, limit);
        return ResponseEntity.status(201)
                .body(BaseResponse.builder()
                              .data(response)
                              .build());
    }

    @PostMapping
    @Secured(value = {"Admin"})
    public ResponseEntity<BaseResponse> insertMovie(@Valid @RequestBody MovieRequest request) {
        var response = this.movieService.insertMovie(request);
        return ResponseEntity.status(201).body(BaseResponse.builder()
                                         .data(response)
                                         .build());
    }

    @PutMapping("/{id}")
    @Secured(value = {"Admin"})
    public ResponseEntity<BaseResponse> updateMovie(@PathVariable("id") String id,
                                                    @Valid @RequestBody MovieRequest request) {
        var response = this.movieService.updateMovie(id, request);
        return ResponseEntity.ok(BaseResponse.builder()
                                         .data(response)
                                         .build());
    }

    @DeleteMapping("/{id}")
    @Secured(value = {"Admin"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") String id) {
        this.movieService.deleteMovie(id, true);
    }
}