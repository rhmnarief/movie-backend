package com.example.movie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie.database.dto.GenreDTO;
import com.example.movie.database.dto.GenreList;
import com.example.movie.database.dto.GenreResponse;
import com.example.movie.database.dto.MovieList;
import com.example.movie.database.entities.Genre;
import com.example.movie.database.repositories.GenreRepository;

import io.micrometer.common.lang.NonNull;

import java.util.*;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<GenreList> getAll() {
        List<GenreList> listGenre = new ArrayList();
        genreRepository.findAll().forEach(genre -> {
            GenreList genreList = new GenreList();
            genreList.setId(genre.getId());
            genreList.setName(genre.getName());
            listGenre.add(genreList);
        });

        return listGenre;
    }

    public GenreResponse getGenreMovieById(@NonNull Long id) {
        GenreResponse genreResponse = new GenreResponse();
        var dataGenre = genreRepository.findById(id).get();
        genreResponse.setId(dataGenre.getId());
        genreResponse.setName(dataGenre.getName());

        List<MovieList> listMovie = new ArrayList();

        dataGenre.getMovies().forEach(movie -> {
            MovieList dataListMovie = new MovieList(movie.getId(), movie.getTitle(), movie.getTitle(),
                    movie.getSummary());
            listMovie.add(dataListMovie);
        });

        genreResponse.setMovies(listMovie);

        return genreResponse;
    }

    public Genre create(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setName(genreDTO.getName());
        return genreRepository.save(genre);
    }

    public Genre updateGenre(@NonNull Long id, GenreDTO genreDTO) {
        Optional<Genre> existinggenre = genreRepository.findById(id);

        if (existinggenre.isPresent()) {
            Genre genre = existinggenre.get();
            genre.setName(genreDTO.getName());
            return genreRepository.save(genre);
        } else {
            return null;
        }
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

}
