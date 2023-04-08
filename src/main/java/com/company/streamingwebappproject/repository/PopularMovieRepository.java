package com.company.streamingwebappproject.repository;

import com.company.streamingwebappproject.models.Movie;
import com.company.streamingwebappproject.models.sections.PopularMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PopularMovieRepository extends JpaRepository<PopularMovie,BigInteger> {

    Optional<PopularMovie> findById(BigInteger id);

}
