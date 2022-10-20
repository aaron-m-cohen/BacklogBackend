package com.bloomtech.backlog.controllers;

import com.bloomtech.backlog.entities.Folder;
import com.bloomtech.backlog.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users/{userId}/folders")
public class FolderController {
    @Autowired
    FolderRepository repo;

    @PostMapping
    public Folder save(@PathVariable(value = "userId") String userId, @RequestBody Folder folder) {
        folder.setUserId(userId);
        return repo.save(folder);
    }

    @GetMapping("{folderId}")
    public Folder findById(@PathVariable(value = "userId") String userId,
                           @PathVariable(value = "folderId") String folderId) {
        return repo.findById(userId, folderId);
    }

    @GetMapping
    public List<Folder> getFoldersForUser(@PathVariable (value = "userId") String userId) {
        return repo.getFoldersForUser(userId);
    }

    @GetMapping("/game")
    public List<Folder> getFoldersForGame(@PathVariable(value = "userId") String userId,
                                          @RequestParam String gameId) {
        return repo.getFoldersForGame(userId, gameId);
    }

    @PutMapping("{folderId}")
    public String update(@RequestBody Folder folder) {
        return repo.update(folder);
    }


    @DeleteMapping("{folderId}")
    public String delete(@PathVariable(value = "userId") String userId,
                         @PathVariable(value = "folderId") String folderId) {
       return repo.delete(userId, folderId);
    }
}
