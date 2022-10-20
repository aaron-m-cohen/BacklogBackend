package com.bloomtech.backlog.controllers;

import com.bloomtech.backlog.entities.User;
import com.bloomtech.backlog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserRepository repo;

    @PostMapping
    public User save(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping("{userId}")
    public User findById(@PathVariable(value = "userId") String userId) {
        return repo.getUser(userId);
    }

    @GetMapping
    public List<User> getUsersForPlatform(@RequestParam String platformId) {
        return repo.getUsersForPlatform(platformId);
    }

    @PutMapping("{userId}")
    public String update(@RequestBody User user) {
        return repo.update(user);
    }

    @DeleteMapping("{userId}")
    public String delete(@PathVariable(value = "userId") String userId) {
        return repo.delete(userId);
    }
}
