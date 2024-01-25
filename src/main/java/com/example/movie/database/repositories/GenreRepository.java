package com.example.movie.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.movie.database.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{
    Page<Genre> findByNameContainingIgnoreCase(String searchTerm, PageRequest page);
}
