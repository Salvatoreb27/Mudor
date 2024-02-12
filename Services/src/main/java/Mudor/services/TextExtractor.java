package Mudor.services;

import org.springframework.stereotype.Service;

@Service
public interface TextExtractor {
    String extractTextFromWikipediaPage(String url);

}

