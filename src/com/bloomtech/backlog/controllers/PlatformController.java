package com.bloomtech.backlog.controllers;

import com.bloomtech.backlog.entities.Folder;
import com.bloomtech.backlog.entities.Platform;
import com.bloomtech.backlog.repositories.FolderRepository;
import com.bloomtech.backlog.repositories.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platforms")
@CrossOrigin(origins = "*")
public class PlatformController {
    @Autowired
    PlatformRepository repo;

    @PostMapping
    public Platform save(@RequestBody Platform platform) {
        return repo.save(platform);
    }

    @GetMapping("{platformId}")
    public Platform findById(@PathVariable(value = "platformId") String platformId) {
        return repo.getPlatform(platformId);
    }

    @GetMapping("/game")
    public List<Platform> getPlatformsForGame(@RequestParam String gameId) {
        return repo.getPlatformsForGame(gameId);
    }

    @GetMapping("/user")
    public List<Platform> getPlatformsForUser(@RequestParam String userId) {
        return repo.getPlatformsForUser(userId);
    }

    @PutMapping("{platformId}")
    public String update(@RequestBody Platform platform) {
        return repo.update(platform);
    }

    @DeleteMapping("{platformId}")
    public String delete(@PathVariable(value = "platformId") String platformId) {
        return repo.delete(platformId);
    }
}
