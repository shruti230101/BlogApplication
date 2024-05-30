package com.example.BlogApplication.repositories;

import com.example.BlogApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
