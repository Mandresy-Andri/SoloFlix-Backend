package com.company.streamingwebappproject.repository;

import com.company.streamingwebappproject.models.Movie;
import com.company.streamingwebappproject.models.sections.PopularMovie;
import com.company.streamingwebappproject.models.sections.TrendingMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface TrendingMovieRepository extends JpaRepository<TrendingMovie,BigInteger> {

    Optional<TrendingMovie> findById(BigInteger id);

}
