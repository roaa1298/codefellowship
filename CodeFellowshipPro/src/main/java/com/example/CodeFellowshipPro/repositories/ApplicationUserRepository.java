package com.example.CodeFellowshipPro.repositories;

import com.example.CodeFellowshipPro.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findUserByUsername(String username);
    ApplicationUser findUserById(Long id);
}
