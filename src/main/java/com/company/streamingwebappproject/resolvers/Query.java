package com.company.streamingwebappproject.resolvers;

import com.company.streamingwebappproject.models.Movie;
import com.company.streamingwebappproject.models.User;
import com.company.streamingwebappproject.models.sections.*;
import com.company.streamingwebappproject.repository.*;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    PopularMovieRepository popularMovieRepository;
    @Autowired
    TrendingMovieRepository trendingMovieRepository;
    @Autowired
    TopRatedMovieRepository topRatedMovieRepository;
    @Autowired
    RomanceMovieRepository romanceMovieRepository;
    @Autowired
    MyListRepository myListRepository;
    @Autowired
    UserRepository userRepository;

    public List<Movie> movies() {
        return movieRepository.findAll();
    }

    public List<Movie> findMovieByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public Movie findMovieById(BigInteger id) {
        return movieRepository.findById(id).orElse(null);
    }

    public List<PopularMovie> popularMovies() {
        return popularMovieRepository.findAll();
    }

    public List<RomanceMovie> romanceMovies() {
        return romanceMovieRepository.findAll();
    }

    public List<TopRatedMovie> topRatedMovies() {
        return topRatedMovieRepository.findAll();
    }

    public List<TrendingMovie> trendingMovies() {
        return trendingMovieRepository.findAll();
    }

    public List<MyList> myLists() {
        return myListRepository.findAll();
    }

    public List<MyList> findMyListByUserEmail(String email) {
        return myListRepository.findByUserEmail(email);
    }

    public List<User> users() {
        return userRepository.findAll();
    }

    public List<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
