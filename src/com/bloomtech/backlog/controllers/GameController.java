package com.bloomtech.backlog.controllers;

import com.bloomtech.backlog.entities.Folder;
import com.bloomtech.backlog.entities.Game;
import com.bloomtech.backlog.repositories.FolderRepository;
import com.bloomtech.backlog.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    GameRepository repo;

    @GetMapping
    public List<Game> findAll() {
        return repo.findAll().stream().toList();
    }
    @GetMapping("{gameId}")
    public Game findById(@PathVariable(value = "gameId") String gameId) {
        return repo.getGame(gameId);
    }

    @GetMapping("/platform")
    public List<Game> getGamesForPlatform(@RequestParam String platformId) {
        return repo.getGamesForPlatform(platformId);
    }

    @GetMapping("/folder/{folderId}")
    public List<Game> getGamesForFolder(@RequestParam String userId, @PathVariable(value = "folderId") String folderId) {
        return repo.getGamesForUserFolder(userId, folderId);
    }
}
