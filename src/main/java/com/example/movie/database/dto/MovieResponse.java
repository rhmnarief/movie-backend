package com.example.movie.database.dto;

import lombok.Data;

import java.util.List;


@Data
public class MovieResponse {
    private Long Id;
    private String title;
    private String director;
    private String summary;
    public List<GenreList> genres;
}
