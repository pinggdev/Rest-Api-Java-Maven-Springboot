package com.restapi.Controller;

import com.restapi.Models.User;
import com.restapi.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ApiController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/")
    public String getPage() {
        return "Welcome";
    }

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody User user) {
        userRepo.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User saved successfully");
        response.put("data", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable long id, @RequestBody User user) {
        Optional<User> userData = userRepo.findById(id);
        if (userData.isPresent()) {
            User updatedUser = userData.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            userRepo.save(updatedUser);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User updated successfully");
            response.put("data", updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User with id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable long id) {
        Optional<User> userData = userRepo.findById(id);
        if (userData.isPresent()) {
            User deleteUser = userData.get();
            userRepo.delete(deleteUser);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User " + id + " deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User with id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        // cek apakah email sudah terdaftar
        if(userRepo.findByEmail(user.getEmail()) != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Email already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // tambahkan user baru ke database
        userRepo.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("data", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        // cek apakah user dengan email yang diberikan sudah terdaftar
        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // cek apakah password yang diberikan cocok dengan password user yang terdaftar
        if (!existingUser.getPassword().equals(user.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // buat response dengan informasi user yang berhasil login
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("data", existingUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
