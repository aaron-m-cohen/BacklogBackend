package com.bloomtech.backlog.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.bloomtech.backlog.entities.FolderGame;
import com.bloomtech.backlog.entities.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FolderRepository {
    @Autowired
    private DynamoDBMapper mapper;

    public Folder findById(String userId, String folderId) {
        return mapper.load(Folder.class, userId, folderId);
    }

    public List<Folder> getFoldersForUser(String userId) {
        Folder folder = new Folder();
        folder.setUserId(userId);
        return mapper.query(Folder.class, new DynamoDBQueryExpression<Folder>().withHashKeyValues(folder));
    }

    public List<Folder> getFoldersForGame(String userId, String gameId) {
        FolderGame folderGame = new FolderGame();
        folderGame.setGameId(gameId);

        DynamoDBQueryExpression<FolderGame> qe = new DynamoDBQueryExpression<FolderGame>()
                .withHashKeyValues(folderGame)
                .withConsistentRead(false)
                .withIndexName(FolderGame.FOLDER_GAME_INDEX);

        PaginatedQueryList<FolderGame> gameFolders = mapper.query(FolderGame.class, qe);

        //ugly, but ok for now. Really more modelled to a SQL-DB, would not use dynamo for this data model in production
        return gameFolders.stream().map(up -> mapper.load(Folder.class, userId, up.getFolderId())).collect(Collectors.toList());
    }

    public FolderGame addGameToFolder(String folderId, String gameId) {
        FolderGame folderGame = new FolderGame();
        folderGame.setFolderId(folderId);
        folderGame.setGameId(gameId);
        mapper.save(folderGame);
        return folderGame;
    }

    public FolderGame removeGameFromFolder(String folderId, String gameId) {
        FolderGame folderGame = mapper.load(FolderGame.class, folderId, gameId);
        mapper.delete(folderGame);
        return folderGame;
    }

    public FolderGame addNotesToFolderGame(String folderId, String gameId, String notes) {
        FolderGame folderGame = mapper.load(FolderGame.class, folderId, gameId);
        if (folderGame != null) {
            folderGame.setNotes(notes);
            mapper.save(folderGame);
        }
        return folderGame;
    }

    //Create
    public Folder save(Folder folder) {
        mapper.save(folder);
        return folder;
    }

    //Update
    public String update(Folder folder) {
        Folder old = mapper.load(Folder.class, folder.getUserId(), folder.getFolderId());
        if (old != null) {
            mapper.save(folder);
        }

        return "Successfully Updated Folder: " + folder.toString();
    }

    // Delete
    public String delete(String userId, String folderId) {
        Folder folder = mapper.load(Folder.class, userId, folderId);
        mapper.delete(folder);
        return "Successfully deleted Contact: " + folder.toString();
    }
}
