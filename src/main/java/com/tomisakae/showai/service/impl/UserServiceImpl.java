package com.tomisakae.showai.service.impl;

import com.tomisakae.showai.entity.User;
import com.tomisakae.showai.repository.UserRepository;
import com.tomisakae.showai.service.UserService;
import com.tomisakae.showai.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(String firebaseUid, String username) {
        User user = new User();
        user.setFirebaseUid(firebaseUid);
        user.setUsername(username);
        return userRepository.save(user);
    }

    @Override
    public User getUserByFirebaseUid(String firebaseUid) {
        return userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
} 