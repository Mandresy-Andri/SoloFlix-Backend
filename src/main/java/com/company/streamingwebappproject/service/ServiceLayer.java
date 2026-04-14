package com.company.streamingwebappproject.service;

import com.company.streamingwebappproject.models.Movie;
import com.company.streamingwebappproject.models.MovieResults;
import com.company.streamingwebappproject.models.Video;
import com.company.streamingwebappproject.models.VideoResults;
import com.company.streamingwebappproject.models.sections.PopularMovie;
import com.company.streamingwebappproject.models.sections.RomanceMovie;
import com.company.streamingwebappproject.models.sections.TopRatedMovie;
import com.company.streamingwebappproject.models.sections.TrendingMovie;
import com.company.streamingwebappproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ServiceLayer {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PopularMovieRepository popularMovieRepository;
    @Autowired
    RomanceMovieRepository romanceMovieRepository;
    @Autowired
    TopRatedMovieRepository topRatedMovieRepository;
    @Autowired
    TrendingMovieRepository trendingMovieRepository;
    @Value("${tmdb.api.key}")
    private String apiKey;

    public ResponseEntity<List<Movie>> findPopularMovies() {
        return findMoviesByApiEndpoint(
                "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey + "&language=en-US&page=1");
    }

    public ResponseEntity<List<Movie>> findTrendingMovies() {
        return findMoviesByApiEndpoint("https://api.themoviedb.org/3/trending/movie/week?api_key=" + apiKey);
    }

    public ResponseEntity<List<Movie>> findTopRatedMovies() {
        return findMoviesByApiEndpoint(
                "https://api.themoviedb.org/3/movie/top_rated?api_key=" + apiKey + "&language=en-US&page=1");
    }

    public ResponseEntity<List<Movie>> findRomanceMovies() {
        return findMoviesByApiEndpoint(
                "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&with_genres=10749");
    }

    // Finds movies from OMDB API given the uri
    public ResponseEntity<List<Movie>> findMoviesByApiEndpoint(String uri) {
        WebClient movieClient = WebClient.create(uri);
        List<Movie> movieResponse = null;
        try {
            Mono<MovieResults> response = movieClient
                    .get()
                    .retrieve()
                    .bodyToMono(MovieResults.class);

            movieResponse = response.block().getResults();
        } catch (WebClientResponseException we) {
            int statusCode = we.getRawStatusCode();
            if (statusCode == 429)
                return new ResponseEntity(movieResponse, HttpStatus.TOO_MANY_REQUESTS);
            else if (statusCode >= 400 && statusCode < 500)
                System.out.println("Client Error");
            else if (statusCode >= 500 && statusCode < 600)
                System.out.println("Server Error");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        findMovieVideo(movieResponse);

        return new ResponseEntity(movieResponse, HttpStatus.OK);
    }

    // Sets the link to the movie trailer for all movies in a movie list
    // Movie trailer link is obtained through OMDB API uri, given the movie id
    public void findMovieVideo(List<Movie> movieResponse) {
        for (Movie movie : movieResponse) {
            WebClient videoClient = WebClient.create("https://api.themoviedb.org/3/movie/" + movie.getReference()
                    + "/videos?api_key=" + apiKey + "&language=en-US");
            List<Video> videoResponse = null;
            try {
                Mono<VideoResults> response = videoClient
                        .get()
                        .retrieve()
                        .bodyToMono(VideoResults.class);

                videoResponse = response.block().getResults();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

            if (videoResponse != null && !videoResponse.isEmpty()) {
                movie.setVideo("https://www.youtube.com/watch?v=" + videoResponse.get(0).getKey());
            } else {
                System.out.println("No videos found for movie with ID: " + movie.getReference());
            }
        }

    }

    public List<Movie> populatePopularMovies() {
        List<Movie> movies = findPopularMovies().getBody();
        for (Movie movie : movies) {
            movieRepository.save(movie);
            PopularMovie popularMovie = new PopularMovie();
            popularMovie.setMovie(movie);
            popularMovieRepository.save(popularMovie);
        }

        return movies;
    }

    public List<Movie> populateTrendingMovies() {
        List<Movie> movies = findTrendingMovies().getBody();
        for (Movie movie : movies) {
            movieRepository.save(movie);
            TrendingMovie trendingMovie = new TrendingMovie();
            trendingMovie.setMovie(movie);
            trendingMovieRepository.save(trendingMovie);
        }

        return movies;
    }

    public List<Movie> populateTopRatedMovies() {
        List<Movie> movies = findTopRatedMovies().getBody();
        for (Movie movie : movies) {
            movieRepository.save(movie);
            TopRatedMovie topRatedMovie = new TopRatedMovie();
            topRatedMovie.setMovie(movie);
            topRatedMovieRepository.save(topRatedMovie);
        }

        return movies;
    }

    public List<Movie> populateRomanceMovies() {
        List<Movie> movies = findRomanceMovies().getBody();
        for (Movie movie : movies) {
            movieRepository.save(movie);
            RomanceMovie romanceMovie = new RomanceMovie();
            romanceMovie.setMovie(movie);
            romanceMovieRepository.save(romanceMovie);
        }

        return movies;
    }

    public List<Movie> searchMovies(String query) {
        // Search TMDB API without auto-caching
        try {
            String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key="
                    + apiKey + "&query=" + query.replace(" ", "+") + "&language=en-US&page=1";
            ResponseEntity<List<Movie>> apiResponse = findMoviesByApiEndpoint(searchUrl);
            List<Movie> apiMovies = apiResponse.getBody();

            if (apiMovies != null && !apiMovies.isEmpty()) {
                // Remove movies missing required data (reference is the TMDB id)
                apiMovies.removeIf(movie -> movie == null || movie.getReference() == null);

                // Set defaults for ALL non-null GraphQL fields on unsaved TMDB movies
                for (Movie movie : apiMovies) {
                    if (movie.getId() == null)
                        movie.setId(movie.getReference());
                    if (movie.getTitle() == null)
                        movie.setTitle("Unknown Title");
                    if (movie.getDescription() == null)
                        movie.setDescription("");
                    if (movie.getDate() == null)
                        movie.setDate("");
                    if (movie.getVideo() == null)
                        movie.setVideo("");
                    // image getter prepends base URL, so null image becomes "https://...null"
                    // which is safe for GraphQL but let's set a placeholder
                    if (movie.getImage() == null || movie.getImage().contains("null")) {
                        movie.setImage("");
                    }
                }
                System.out.println("TMDB search returned " + apiMovies.size() + " results for: " + query);
                return apiMovies;
            }
        } catch (Exception e) {
            System.out.println("Search API error: " + e.getMessage());
            e.printStackTrace();
        }

        return java.util.Collections.emptyList();
    }

    public Movie cacheMovie(String movieId) {
        // Check if movie already exists in database by TMDB reference
        try {
            java.math.BigInteger refId = new java.math.BigInteger(movieId);
            java.util.Optional<Movie> existing = movieRepository.findByReference(refId);
            if (existing.isPresent()) {
                return existing.get();
            }

            // Fetch movie details from TMDB API
            String detailUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey
                    + "&language=en-US";
            WebClient movieClient = WebClient.create(detailUrl);

            Movie movie = null;
            try {
                Mono<Movie> response = movieClient
                        .get()
                        .retrieve()
                        .bodyToMono(Movie.class);
                movie = response.block();
            } catch (Exception e) {
                System.out.println("Error fetching movie details: " + e.getMessage());
                return null;
            }

            if (movie != null) {
                // Fetch and set the video URL
                WebClient videoClient = WebClient.create("https://api.themoviedb.org/3/movie/" + movieId
                        + "/videos?api_key=" + apiKey + "&language=en-US");
                List<Video> videoResponse = null;
                try {
                    Mono<VideoResults> response = videoClient
                            .get()
                            .retrieve()
                            .bodyToMono(VideoResults.class);
                    videoResponse = response.block().getResults();
                } catch (Exception e) {
                    System.out.println("Error fetching video: " + e.getMessage());
                }

                if (videoResponse != null && !videoResponse.isEmpty()) {
                    movie.setVideo("https://www.youtube.com/watch?v=" + videoResponse.get(0).getKey());
                } else {
                    // Set empty video URL if no trailer found
                    movie.setVideo("");
                    System.out.println("No trailer found for movie: " + movie.getTitle());
                }

                // Save movie even if no video trailer
                return movieRepository.save(movie);
            }
        } catch (Exception e) {
            System.out.println("Error caching movie: " + e.getMessage());
        }

        return null;
    }
}