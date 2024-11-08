package com.tomisakae.showai.repository;

import com.tomisakae.showai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirebaseUid(String firebaseUid);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByFirebaseUid(String firebaseUid);
}
