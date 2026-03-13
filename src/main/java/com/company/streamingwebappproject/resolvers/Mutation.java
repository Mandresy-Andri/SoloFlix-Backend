package com.company.streamingwebappproject.resolvers;

import com.company.streamingwebappproject.models.Movie;
import com.company.streamingwebappproject.models.User;
import com.company.streamingwebappproject.models.sections.MyList;
import com.company.streamingwebappproject.repository.MovieRepository;
import com.company.streamingwebappproject.repository.MyListRepository;
import com.company.streamingwebappproject.repository.UserRepository;
import com.company.streamingwebappproject.service.ServiceLayer;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private UserRepository repo;

    @Autowired
    private MyListRepository myListRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ServiceLayer serviceLayer;

    public User addUser(String email, String username, String password, Boolean enabled) {
        User user = new User(email, username, password, enabled);
        return repo.save(user);
    }

    public User updateUser(String email, String username, String password, Boolean enabled) {
        User user = new User(email, username, password, enabled);
        return repo.save(user);
    }

    public boolean deleteUserByEmail(String email) {
        repo.deleteById(email);
        return true;
    }

    public MyList addToMyList(String userEmail, String movieId, String notes,
                              Double userRating, Boolean watched, Boolean recommended) {
        User user = repo.findById(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        Movie movie = movieRepository.findById(new BigInteger(movieId))
                .orElseThrow(() -> new RuntimeException("Movie not found: " + movieId));

        Optional<MyList> existing = myListRepository.findByUserEmailAndMovieId(userEmail, new BigInteger(movieId));
        if (existing.isPresent()) {
            throw new RuntimeException("Movie already in list");
        }

        MyList myList = new MyList();
        myList.setUser(user);
        myList.setMovie(movie);
        myList.setNotes(notes);
        myList.setUserRating(userRating);
        myList.setWatched(watched != null ? watched : false);
        myList.setRecommended(recommended);

        return myListRepository.save(myList);
    }

    public MyList updateMyListItem(String id, String notes, Double userRating,
                                   Boolean watched, Boolean recommended) {
        MyList myList = myListRepository.findById(new BigInteger(id))
                .orElseThrow(() -> new RuntimeException("MyList item not found: " + id));

        if (notes != null) myList.setNotes(notes);
        if (userRating != null) myList.setUserRating(userRating);
        if (watched != null) myList.setWatched(watched);
        if (recommended != null) myList.setRecommended(recommended);

        return myListRepository.save(myList);
    }

    public Boolean removeFromMyList(String id) {
        myListRepository.deleteById(new BigInteger(id));
        return true;
    }

    public MyList toggleWatched(String id) {
        MyList myList = myListRepository.findById(new BigInteger(id))
                .orElseThrow(() -> new RuntimeException("MyList item not found: " + id));

        myList.setWatched(!myList.getWatched());
        return myListRepository.save(myList);
    }

    public Movie cacheMovie(String movieId) {
        return serviceLayer.cacheMovie(movieId);
    }
}
