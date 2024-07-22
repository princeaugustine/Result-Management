package com.udom.school.repostory;

import com.udom.school.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Studentrep extends JpaRepository<Students, String> {
    Students findByCandidate(String candidate);

    Students findByCandidateAndPassword(String candidate, String password);
}
