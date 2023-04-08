package com.company.streamingwebappproject.controllers;

import com.company.streamingwebappproject.models.Movie;
import com.company.streamingwebappproject.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping(value = "/greeter")
    @ResponseStatus(HttpStatus.OK)
    public void greet(Principal principal) {
        System.out.println("The user logged in is"+principal.getName());
    }

    @GetMapping(value = "/movies/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getPopularMovies() {
        return serviceLayer.findPopularMovies().getBody();
    }

    @GetMapping(value = "/movies/trending")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getTrendingMovies() {
        return serviceLayer.findTrendingMovies().getBody();
    }

    @GetMapping(value = "/movies/toprated")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getTopRatedMovies() {
        return serviceLayer.findTopRatedMovies().getBody();
    }

    @GetMapping(value = "/movies/similar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getRomanceMovies() {
        return serviceLayer.findRomanceMovies().getBody();
    }

    @PostMapping(value = "/movies/popular")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Movie> populatePopularMovies() {
        return serviceLayer.populatePopularMovies();
    }

    @PostMapping(value = "/movies/trending")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Movie> populateTrendingMovies() {
        return serviceLayer.populateTrendingMovies();
    }

    @PostMapping(value = "/movies/toprated")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Movie> populateTopRatedMovies() {
        return serviceLayer.populateTopRatedMovies();
    }

    @PostMapping(value = "/movies/romance")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Movie> populateRomanceMovies() {
        return serviceLayer.populateRomanceMovies();
    }
}
