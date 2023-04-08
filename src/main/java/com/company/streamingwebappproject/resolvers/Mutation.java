package com.company.streamingwebappproject.resolvers;

import com.company.streamingwebappproject.models.User;
import com.company.streamingwebappproject.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private UserRepository repo;

    public User addUser(String email, String username, String password, Boolean enabled) {
        User user =new User(email,username,password,enabled);
        return repo.save(user);
    }

    public User updateUser(String email, String username, String password, Boolean enabled) {
        User user =new User(email,username,password,enabled);
        return repo.save(user);
    }

    public boolean deleteUserByEmail(String email) {
        repo.deleteById(email);
        return true;
    }
}
