package com.example.campusmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST Controller - handles all HTTP requests for users
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * POST /api/users - create user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            LocalDate dob = LocalDate.parse(body.get("dob"));
            String email = body.get("email");
            String address = body.get("address");
            User user = userService.createUser(name, dob, email, address);
            return ResponseEntity.status(201).body(user);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/users - get all users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * GET /api/users/{id} - get one user
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/users/{id} - update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            LocalDate dob = LocalDate.parse(body.get("dob"));
            String email = body.get("email");
            String address = body.get("address");
            return userService.updateUser(id, name, dob, email, address)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/users/{id} - delete user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}