package com.anshu.user_service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User getUserById(Integer id) {
        return userRepo.findById(id)
                .orElse(null);
    }

}
