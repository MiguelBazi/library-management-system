package com.library.library_management_system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenLibraryService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String OPEN_LIBRARY_URL =
            "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";

    public String fetchAuthorName(String isbn) {
        try {
            log.info("Fetching author name from Open Library for ISBN: {}", isbn);

            String url = OPEN_LIBRARY_URL.replace("{isbn}", isbn);
            String response = restTemplate.getForObject(url, String.class);

            if (response == null || response.isEmpty()) {
                log.warn("No response from Open Library for ISBN: {}", isbn);
                return null;
            }

            JsonNode root = objectMapper.readTree(response);
            JsonNode bookNode = root.get("ISBN:" + isbn);

            if (bookNode == null) {
                log.warn("No book found in Open Library for ISBN: {}", isbn);
                return null;
            }

            JsonNode authors = bookNode.get("authors");
            if (authors == null || !authors.isArray() || authors.isEmpty()) {
                log.warn("No authors found in Open Library for ISBN: {}", isbn);
                return null;
            }

            String authorName = authors.get(0).get("name").asText();
            log.info("Found author name: {} for ISBN: {}", authorName, isbn);
            return authorName;

        } catch (Exception e) {
            log.error("Error fetching author from Open Library: {}", e.getMessage());
            return null;
        }
    }
}