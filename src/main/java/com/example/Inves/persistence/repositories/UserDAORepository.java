package com.example.Inves.persistence.repositories;

import com.example.Inves.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:32
 */
@Repository
public interface UserDAORepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    List<User> findAllByUsername(String username);
}
