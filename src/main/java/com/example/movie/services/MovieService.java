package com.example.movie.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.movie.database.dto.GenreList;
import com.example.movie.database.dto.MovieDTO;
import com.example.movie.database.dto.MovieResponse;
import com.example.movie.database.entities.Genre;
import com.example.movie.database.entities.Movie;
import com.example.movie.database.repositories.GenreRepository;
import com.example.movie.database.repositories.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;

    public Page<MovieResponse> getDataMoviePages(Page<Movie> pageMovie) {
        Page<MovieResponse> movieResponsePage = pageMovie.map(this::convertToMovieResponse);
        return movieResponsePage;
    }

    private MovieResponse convertToMovieResponse(Movie movie) {
        MovieResponse dataMovieResponse = new MovieResponse();
        dataMovieResponse.setId(movie.getId());
        dataMovieResponse.setTitle(movie.getTitle());
        dataMovieResponse.setDirector(movie.getDirector());
        dataMovieResponse.setSummary(movie.getSummary());
        List<GenreList> listGenre = new ArrayList();

        movie.getGenres().forEach(genre -> {
            GenreList genres = new GenreList();
            genres.setId(genre.getId());
            genres.setName(genre.getName());
            listGenre.add(genres);
        });
        dataMovieResponse.setGenres(listGenre);
        return dataMovieResponse;
    }

   
    public Long createMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDirector(movieDTO.getDirector());
        movie.setSummary(movieDTO.getSummary());
        Set<Genre> genres = new HashSet<Genre>();

        movieDTO.getGenre().forEach(i -> {
            genres.add(genreRepository.findById(i).get());
        });
        movie.setGenres(genres);
        return movieRepository.save(movie).getId();
    }

    public MovieResponse getDetailMovie(@NonNull Long id) {
        Movie dataMovie = movieRepository.findById(id).get();
        MovieResponse responseMovie = new MovieResponse();
        responseMovie.setId(dataMovie.getId());
        responseMovie.setTitle(dataMovie.getTitle());
        responseMovie.setDirector(dataMovie.getDirector());
        responseMovie.setSummary(dataMovie.getSummary());

        List<GenreList> listGenre = new ArrayList();

        dataMovie.getGenres().forEach(genre -> {
            GenreList dataListGenre = new GenreList();
            dataListGenre.setId(genre.getId());
            dataListGenre.setName(genre.getName());
            listGenre.add(dataListGenre);
        });

        responseMovie.setGenres(listGenre);
        return responseMovie;
    }
}
