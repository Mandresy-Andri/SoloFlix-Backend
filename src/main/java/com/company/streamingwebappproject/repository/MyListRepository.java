package com.company.streamingwebappproject.repository;

import com.company.streamingwebappproject.models.sections.MyList;
import com.company.streamingwebappproject.models.sections.PopularMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface MyListRepository extends JpaRepository<MyList,BigInteger> {

    Optional<MyList> findById(BigInteger id);

    List<MyList> findByUserEmail(String userEmail);

}
