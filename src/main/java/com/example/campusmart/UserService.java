package com.example.campusmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service - handles user business logic and age calculation
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new user and calculate age from DOB
     */
    public User createUser(String name, LocalDate dob, String email, String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (dob == null || dob.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid date of birth");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        User user = new User(name, dob, email, address);
        System.out.println("[INFO] User created: " + name + ", Age: " + user.getAge());
        return userRepository.save(user);
    }

    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by ID
     */
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Update user
     */
    public Optional<User> updateUser(int id, String name, LocalDate dob, String email, String address) {
        Optional<User> found = userRepository.findById(id);
        found.ifPresent(u -> {
            u.setName(name);
            u.setDob(dob);
            u.setEmail(email);
            u.setAddress(address);
            userRepository.save(u);
            System.out.println("[INFO] User updated: " + id);
        });
        return found;
    }

    /**
     * Delete user
     */
    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            System.out.println("[INFO] User deleted: " + id);
            return true;
        }
        System.out.println("[WARN] User not found: " + id);
        return false;
    }
}