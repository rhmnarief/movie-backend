package com.example.movie.controllers;

import com.example.movie.database.repositories.GenreRepository;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.movie.database.dto.GenreDTO;
import com.example.movie.database.dto.GenreList;
import com.example.movie.database.dto.GenreResponse;
import com.example.movie.database.entities.Genre;
import com.example.movie.services.GenreService;
import com.example.movie.utils.response.CustomResponse;
import com.example.movie.utils.response.ResponseUtil;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping(value = "/genre")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GenreService genreService;

    @GetMapping
    public List<GenreList> getAllGenre() {
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Object>> getById(@PathVariable @NonNull Long id) {
        GenreResponse dataGenre = genreService.getGenreMovieById(id);
        CustomResponse<Object> customResponse = new CustomResponse<>("success", "success get data", dataGenre);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createGenre(@RequestBody GenreDTO genreDTO) {
        try {
            genreService.create(genreDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movie created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create movie: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Object>> updateGenre(@PathVariable @NonNull Long id, @RequestBody GenreDTO genreDTO) {
        CustomResponse<Object> customResponse;
        try {
            Genre genre = genreService.updateGenre(id, genreDTO);
            if (genre != null) {
                customResponse = new CustomResponse<>(
                        "success",
                        "Update successfully",
                        genre.getId());
                return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
            } else {
                customResponse = new CustomResponse<>(
                        "failed",
                        "Data not found",
                        genre.getId());
                return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            customResponse = new CustomResponse<>(
                    "failed",
                    e.getMessage().toString(),
                    "Error update genre");
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Object>> deleteGenre(@PathVariable @NonNull Long id) {
        CustomResponse<Object> customResponse;
        try {
            Optional<Genre> dataGenre = genreRepository.findById(id);

            if (dataGenre.isEmpty()) {
                customResponse = new CustomResponse<>("failed", "Data is not found", "");
                return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
            } else {
                genreRepository.deleteById(id);
                customResponse = new CustomResponse<>("success", "Data deleted succesfully", "");
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            }

        } catch (Exception e) {
            customResponse = new CustomResponse<>(
                    "failed",
                    e.getMessage().toString(),
                    "Error delete genre");
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
