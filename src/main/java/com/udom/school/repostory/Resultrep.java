package com.udom.school.repostory;

import com.udom.school.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Resultrep extends JpaRepository<Result, String> {
    List<Result> findByCandidate(String candidate);

    Result findByCandidateAndDate(String candidate, String date);
}
