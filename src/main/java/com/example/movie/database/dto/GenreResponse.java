package com.example.movie.database.dto;
import java.util.List;

import lombok.Data;

@Data
public class GenreResponse {
    private Long id;
    private String name;
    public List<MovieList> movies;
}

