package com.bloomtech.backlog.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.bloomtech.backlog.entities.FolderGame;
import com.bloomtech.backlog.entities.Game;
import com.bloomtech.backlog.entities.PlatformGame;
import com.bloomtech.backlog.exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GameRepository {

    @Autowired
    private DynamoDBMapper mapper;


    public List<Game> findAll() {
        return mapper.scan(Game.class, new DynamoDBScanExpression());
    }

    public Game getGame(String gameId) {
        Game game = mapper.load(Game.class, gameId);

        if (game == null) {
            throw new GameNotFoundException("Cannot find game for given Id. Please Try Again.");
        }

        return game;
    }

    public List<Game> getGamesForPlatform(String platformId) {
        PlatformGame platformGame = new PlatformGame();
        platformGame.setPlatformId(platformId);

        DynamoDBQueryExpression<PlatformGame> qe = new DynamoDBQueryExpression<PlatformGame>()
                .withHashKeyValues(platformGame)
                .withConsistentRead(false);

        PaginatedQueryList<PlatformGame> platformGames = mapper.query(PlatformGame.class, qe);

        //ugly, but ok for now. Really more modelled to a SQL-DB, would not use dynamo for this data model in production
        return platformGames.stream().map(up -> mapper.load(Game.class, up.getGameId())).collect(Collectors.toList());
    }

    public List<Game> getGamesForUserFolder(String userId, String folderId) {
        FolderGame folderGame = new FolderGame();
        folderGame.setFolderId(folderId);

        DynamoDBQueryExpression<FolderGame> qe = new DynamoDBQueryExpression<FolderGame>()
                .withHashKeyValues(folderGame)
                .withConsistentRead(false);

        PaginatedQueryList<FolderGame> folderGames = mapper.query(FolderGame.class, qe);

        //ugly, but ok for now. Really more modelled to a SQL-DB, would not use dynamo for this data model in production
        return folderGames.stream().map(up -> mapper.load(Game.class, up.getGameId())).collect(Collectors.toList());
    }

    public Game save(Game game) {
        mapper.save(game);
        return game;
    }

    public String update(Game game) {
        mapper.save(game);

        return "Successfully Updated Folder: " + game.toString();
    }

    // Delete
    public String delete(String gameId) {
        Game game = mapper.load(Game.class, gameId);
        mapper.delete(game);
        return "Successfully deleted Contact: " + game.toString();
    }
}
