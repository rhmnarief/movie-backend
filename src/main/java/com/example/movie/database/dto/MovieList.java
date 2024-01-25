package com.example.movie.database.dto;

public class MovieList {
    public Long id;
    public String title;
    public String director;
    public String summary;

    public MovieList(Long id, String title, String director, String summary) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.summary = summary;
    }
}