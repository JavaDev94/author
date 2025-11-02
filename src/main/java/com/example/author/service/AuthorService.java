package com.example.author.service;

import com.example.author.client.OpenLibraryClient;
import com.example.author.model.dto.AuthorResponse;
import com.example.author.model.dto.WorkResponse;
import com.example.author.model.entity.Author;
import com.example.author.model.entity.Work;
import com.example.author.repository.AuthorRepository;
import com.example.author.repository.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final WorkRepository workRepository;
    private final OpenLibraryClient openLibraryClient;

    public AuthorService(AuthorRepository authorRepository, WorkRepository workRepository, OpenLibraryClient openLibraryClient) {
        this.authorRepository = authorRepository;
        this.workRepository = workRepository;
        this.openLibraryClient = openLibraryClient;
    }

    public AuthorResponse findAuthorByName(String name) {
        Author authorDto = authorRepository.findByNameIgnoreCase(name)
                .or(() -> {
                    Optional<Author> externalAuthor = openLibraryClient.searchAuthorByName(name);
                    externalAuthor.ifPresent(authorRepository::save);
                    return externalAuthor;
                })
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return new AuthorResponse(authorDto.getId(), authorDto.getName(), authorDto.getCreatedAt().toString());
    }

    public List<WorkResponse> findWorksByAuthorId(Long authorId) {
        Author authorDto = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        List<Work> worksDtoList = workRepository.findByAuthorId(authorId);
        if (!worksDtoList.isEmpty()) return worksDtoList.stream()
                .map(w -> new WorkResponse(w.getId(), w.getTitle(), w.getCreatedAt().toString()))
                .toList();

        List<Work> externalWorks = openLibraryClient.searchWorksByAuthor(authorDto.getExternalId(), authorDto);
        workRepository.saveAll(externalWorks);
        return externalWorks.stream()
                .map(w -> new WorkResponse(w.getId(), w.getTitle(), w.getCreatedAt().toString()))
                .toList();
    }
}

