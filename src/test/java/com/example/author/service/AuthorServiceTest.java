package com.example.finance.service;

import com.example.finance.client.OpenLibraryClient;
import com.example.finance.model.dto.AuthorResponse;
import com.example.finance.model.entity.Author;
import com.example.finance.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private OpenLibraryClient openLibraryClient;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("J. K. Rowling");
    }


    @Test
    void shouldReturnAuthorFromDatabase() {
        when(authorRepository.findByNameIgnoreCase("J K Rowling"))
                .thenReturn(Optional.of(author));

        AuthorResponse response = authorService.findAuthorByName("J K Rowling");

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("J. K. Rowling");
        verify(openLibraryClient, never()).searchAuthorByName(any());
    }

    @Test
    void shouldFetchAuthorFromExternalApiIfNotInDatabase() {
        when(authorRepository.findByNameIgnoreCase("J K Rowling"))
                .thenReturn(Optional.empty());
        when(openLibraryClient.searchAuthorByName("J K Rowling"))
                .thenReturn(Optional.of(author));

        AuthorResponse response = authorService.findAuthorByName("J K Rowling");

        assertThat(response.getName()).isEqualTo("J. K. Rowling");
        verify(authorRepository).save(author);
    }

    @Test
    void shouldThrowExceptionWhenAuthorNotFoundAnywhere() {
        when(authorRepository.findByNameIgnoreCase("Unknown"))
                .thenReturn(Optional.empty());
        when(openLibraryClient.searchAuthorByName("Unknown"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findAuthorByName("Unknown"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Author not found");
    }
}
