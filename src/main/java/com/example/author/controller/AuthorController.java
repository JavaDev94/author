package com.example.finance.controller;

import com.example.finance.model.dto.AuthorResponse;
import com.example.finance.model.dto.WorkResponse;
import com.example.finance.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/search")
    public ResponseEntity<AuthorResponse> searchAuthor(@RequestParam String name) {
        return ResponseEntity.ok(authorService.findAuthorByName(name));
    }

    @GetMapping("/{authorId}/works")
    public ResponseEntity<List<WorkResponse>> getAuthorWorks(@PathVariable Long authorId) {
        return ResponseEntity.ok(authorService.findWorksByAuthorId(authorId));
    }
}

