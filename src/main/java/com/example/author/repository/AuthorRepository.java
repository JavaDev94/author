package com.example.finance.repository;

import com.example.finance.model.entity.Author;
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
