package com.example.demo.controller;



import com.example.demo.serrvice.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String longUrl) {
        String shortUrl = urlShortenerService.shortenUrl(longUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateShortUrl(@RequestParam String shortUrl, @RequestParam String newLongUrl) {
        boolean updated = urlShortenerService.updateShortUrl(shortUrl, newLongUrl);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/destination")
    public ResponseEntity<String> getLongUrl(@RequestParam String shortUrl) {
        Optional<String> longUrl = urlShortenerService.getLongUrl(shortUrl);
        return longUrl.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/updateExpiry")
    public ResponseEntity<Boolean> updateExpiry(@RequestParam String shortUrl, @RequestParam int daysToAdd) {
        boolean updated = urlShortenerService.updateExpiry(shortUrl, daysToAdd);
        return ResponseEntity.ok(updated);
    }
}
