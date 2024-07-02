package com.example.demo.controller;


import com.example.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class RedirectController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        Optional<String> longUrl = urlShortenerService.getLongUrl("http://localhost:8080/" + shortUrl);
        if (longUrl.isPresent()) {
            response.sendRedirect(longUrl.get());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
