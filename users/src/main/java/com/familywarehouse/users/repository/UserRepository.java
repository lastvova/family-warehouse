package com.familywarehouse.users.repository;

import com.familywarehouse.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    @Modifying
    void deleteById(Long userId);
}
