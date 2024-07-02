package com.example.demo.urlshortener.model;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.example.demo.urlshortener.model.GenerationType.*;

@Entity
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String shortUrl;
    private String longUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expiryDate;

    public UrlMapping(Long id) {
        this.id = id;
    }

    public UrlMapping(String shortUrl, String longUrl, LocalDateTime creationDate, LocalDateTime expiryDate) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
    }

    public void setLongUrl(String newLongUrl) {
    }

    public String getLongUrl() {
    }

    public Instant getExpiryDate() {
    }


    // Getters and Setters
}
