package com.company.streamingwebappproject.repository;

import com.company.streamingwebappproject.models.Movie;
import com.company.streamingwebappproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByEmail(String email);

}
