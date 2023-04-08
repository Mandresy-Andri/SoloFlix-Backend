package com.company.streamingwebappproject.repository;

import com.company.streamingwebappproject.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, BigInteger> {

    List<Movie> findByTitleContainingIgnoreCase(String title);

}
