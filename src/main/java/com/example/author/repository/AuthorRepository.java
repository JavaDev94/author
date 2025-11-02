package com.example.author.repository;

import com.example.author.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("""
        SELECT a FROM Author a
        WHERE LOWER(REPLACE(REPLACE(a.name, '.', ''), ' ', '')) =
              LOWER(REPLACE(REPLACE(:name, '.', ''), ' ', ''))
    """)
    Optional<Author> findByNameIgnoreCase(String name);
}
