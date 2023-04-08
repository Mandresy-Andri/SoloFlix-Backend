package com.company.streamingwebappproject.repository;

import com.company.streamingwebappproject.models.Movie;
import com.company.streamingwebappproject.models.sections.RomanceMovie;
import com.company.streamingwebappproject.models.sections.TrendingMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface RomanceMovieRepository extends JpaRepository<RomanceMovie,BigInteger> {

    Optional<RomanceMovie> findById(BigInteger id);

}
