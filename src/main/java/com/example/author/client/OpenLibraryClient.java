package com.example.finance.client;

import com.example.finance.config.OpenLibraryProperties;
import com.example.finance.model.entity.Author;
import com.example.finance.model.entity.Work;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OpenLibraryClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final OpenLibraryProperties properties;

    public OpenLibraryClient(OpenLibraryProperties properties) {
        this.properties = properties;
    }

    public Optional<Author> searchAuthorByName(String name) {
        String url = properties.getBaseUrl() + properties.getAuthorsSearchPath();
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class, name);
        List<Map<String, Object>> docs = (List<Map<String, Object>>) response.getBody().get("docs");
        if (docs == null || docs.isEmpty()) return Optional.empty();

        Map<String, Object> authorData = docs.get(0);
        Author author = new Author();
        author.setExternalId((String) authorData.get("key"));
        author.setName((String) authorData.get("name"));
        return Optional.of(author);
    }

    public List<Work> searchWorksByAuthor(String externalId, Author author) {
        String url = properties.getBaseUrl() + properties.getWorksPath();
        String cleanId = externalId.replace("/authors/", "");
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class, cleanId);
        List<Map<String, Object>> entries = (List<Map<String, Object>>) response.getBody().get("entries");

        if (entries == null) return List.of();

        return entries.stream()
                .map(e -> new Work(null, author, (String) e.get("title")))
                .toList();
    }
}


