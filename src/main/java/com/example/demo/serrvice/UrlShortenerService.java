package com.example.demo.serrvice;

import com.example.demo.urlshortener.model.UrlMapping;
import com.example.demo.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {

    private static final String BASE_URL = "http://localhost:8080/";
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    private Random random = new Random();

    public String shortenUrl(String longUrl) {
        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlMappingRepository.findByShortUrl(shortUrl).isPresent());

        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime expiryDate = creationDate.plusMonths(10);

        UrlMapping urlMapping = new UrlMapping(BASE_URL + shortUrl, longUrl, creationDate, expiryDate);
        urlMappingRepository.save(urlMapping);

        return BASE_URL + shortUrl;
    }

    public Optional<String> getLongUrl(String shortUrl) {
        return urlMappingRepository.findByShortUrl(shortUrl)
                .filter(urlMapping -> urlMapping.getExpiryDate().isAfter(Instant.from(LocalDateTime.now())))
                .map(UrlMapping::getLongUrl);
    }

    public boolean updateShortUrl(String shortUrl, String newLongUrl) {
        Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMappingOptional.isPresent()) {
            UrlMapping urlMapping = urlMappingOptional.get();
            urlMapping.setLongUrl(newLongUrl);
            urlMappingRepository.save(urlMapping);
            return true;
        }
        return false;
    }

    public boolean updateExpiry(String shortUrl, int daysToAdd) {
        Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMappingOptional.isPresent()) {
            UrlMapping urlMapping = urlMappingOptional.get();
            urlMapping.getExpiryDate(urlMapping.getExpiryDate().plus(daysToAdd))
            urlMappingRepository.save(urlMapping);
            return true;
        }
        return false;
    }

    private String generateShortUrl() {
        StringBuilder sb = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            sb.append(ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));
        }
        return sb.toString();
    }
}
