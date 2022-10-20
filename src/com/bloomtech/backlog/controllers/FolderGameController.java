package com.bloomtech.backlog.controllers;


import com.bloomtech.backlog.entities.FolderGame;
import com.bloomtech.backlog.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/folders/{folderId}")
public class FolderGameController {

    @Autowired
    private FolderRepository repo;

    @PostMapping
    public FolderGame save(@PathVariable(value = "folderId") String folderId, @RequestParam String gameId) {
        return repo.addGameToFolder(folderId, gameId);
    }

    @PutMapping
    public FolderGame update(@PathVariable(value = "folderId") String folderId,
                             @RequestParam String gameId, @RequestBody FolderGame folderGame) {
        return repo.addNotesToFolderGame(folderId, gameId, folderGame.getNotes());
    }
    @DeleteMapping
    public FolderGame delete(@PathVariable(value = "folderId") String folderId, @RequestParam String gameId) {
        return repo.removeGameFromFolder(folderId, gameId);
    }
}
