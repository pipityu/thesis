package com.uni.thesis.repository;


import com.uni.thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    //Ez nem figyeli a kis-nagy betuket. Szükseg eseten nativ query-t kell csinalni felülirni ezt!
    Optional<User> findUserByUsername(String username);
}
