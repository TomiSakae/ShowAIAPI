package com.tomisakae.showai.service;

import com.tomisakae.showai.entity.User;

public interface UserService {
    User createUser(String firebaseUid, String username);
    User getUserByFirebaseUid(String firebaseUid);
    User getUserById(Long id);
    boolean existsByUsername(String username);
}
