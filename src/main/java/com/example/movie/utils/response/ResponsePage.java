package com.example.movie.utils.response;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class ResponsePage {
    private Integer page;
    private Integer rows;
    private Integer totalPages;
    private Long totalRows;
}
