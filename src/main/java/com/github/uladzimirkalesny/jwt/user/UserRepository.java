package com.github.uladzimirkalesny.jwt.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User repository interface for CRUD operations on users table in the database using JPA.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user by email
     *
     * @param email user email
     * @return user
     */
    Optional<User> findByEmail(String email);

}
