package com.example.finance.repository;

import com.example.finance.model.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findByAuthorId(Long authorId);
}
