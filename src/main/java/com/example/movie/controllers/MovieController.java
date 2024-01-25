package com.example.movie.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.movie.constants.CommonConstant;
import com.example.movie.database.dto.MovieDTO;
import com.example.movie.database.entities.Movie;
import com.example.movie.database.repositories.MovieRepository;
import com.example.movie.services.MovieService;
import com.example.movie.utils.response.ApiPagedResponse;
import com.example.movie.utils.response.CustomResponse;
import com.example.movie.utils.response.ResponseUtil;

import org.springframework.lang.NonNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.movie.database.dto.MovieResponse;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ResponseUtil<MovieResponse> responseUtil;


    @GetMapping
    public ResponseEntity<ApiPagedResponse<MovieResponse>> getPageMovieResponse(
            @RequestParam(name = "q", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "1") Integer page) {
        PageRequest pageable = PageRequest.of(--page, CommonConstant.ROWS_PER_PAGE,
                Sort.by(Sort.Direction.ASC, "title"));
        Page<MovieResponse> pageMovieResponse;
        Page<Movie> pageMovie;
        if (keyword.isEmpty()) {
            pageMovie = movieRepository.findAll(pageable);
            pageMovieResponse = movieService.getDataMoviePages(pageMovie);
        } else {
            pageMovie = movieRepository.findByTitleContainingIgnoreCase(keyword, pageable);
            pageMovieResponse = movieService.getDataMoviePages(pageMovie);
        }

        return responseUtil.build(pageMovieResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Object>> getDetailMovie(@NonNull @PathVariable Long id) {
        CustomResponse<Object> customResponse;
        try {
            var dataMovie = movieService.getDetailMovie(id);
            customResponse = new CustomResponse<>("success", "", dataMovie);
            return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            customResponse = new CustomResponse<>("error", e.getMessage().toString(), "");
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Object>> createMovie(@RequestBody @NonNull MovieDTO movieDTO) {
        CustomResponse<Object> customResponse;
        try {
            var dataMovie = movieService.createMovie(movieDTO);
            customResponse = new CustomResponse<>("success", "success created data", dataMovie);
            return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            customResponse = new CustomResponse<>("error", e.getMessage().toString(), "");
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Object>> deleteGenre(@PathVariable Long id) {
        CustomResponse<Object> customResponse;
        if (id == null) {
            customResponse = new CustomResponse<>("failed", "Id must send", "");
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> dataMovie = movieRepository.findById(id);

        if (dataMovie.isEmpty()) {
            customResponse = new CustomResponse<>("failed", "Data is not found", "");
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        } else {
            movieRepository.deleteById(id);
            customResponse = new CustomResponse<>("success", "Data deleted succesfully", "");
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }

    }

}
