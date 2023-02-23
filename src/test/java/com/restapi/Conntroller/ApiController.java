package com.restapi.Conntroller;

import com.restapi.Models.User;
import com.restapi.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        User updatedUser = userRepo.findById(id).get();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        userRepo.save(updatedUser);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        response.put("data", updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable long id) {
        User deleteUser = userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User " + id + " deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
