package com.example.movie.database.dto;

import java.util.Set;
import lombok.Data;


@Data
public class MovieDTO {
    private String title;
    private String director;
    private String summary;
    private Set<Long> genre;
}
