package com.example.webfejlesztes_project.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    public Long countById(Integer id);
    User findByUsername(String username);
}
