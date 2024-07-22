package com.udom.school.repostory;

import com.udom.school.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, String> {
    Admin findByUsernameAndPassword(String username, String password);
}
