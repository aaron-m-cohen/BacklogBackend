package com.bloomtech.backlog.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.bloomtech.backlog.entities.Game;
import com.bloomtech.backlog.entities.Platform;
import com.bloomtech.backlog.entities.PlatformGame;
import com.bloomtech.backlog.entities.UserPlatform;
import com.bloomtech.backlog.exceptions.PlatformNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PlatformRepository {

    @Autowired
    private DynamoDBMapper mapper;


    public Platform getPlatform(String platformId) {
        Platform platform = mapper.load(Platform.class, platformId);

        if (platform == null) {
            throw new PlatformNotFoundException("No platform with this Id. Please try again.");
        }

        return platform;
    }

    public List<Platform> getPlatformsForGame(String gameId) {
        PlatformGame platformGame = new PlatformGame();
        platformGame.setGameId(gameId);

        DynamoDBQueryExpression<PlatformGame> qe = new DynamoDBQueryExpression<PlatformGame>()
                .withHashKeyValues(platformGame)
                .withConsistentRead(false)
                .withIndexName(PlatformGame.GAMES_PLATFORMS_INDEX);

        PaginatedQueryList<PlatformGame> gamePlatforms = mapper.query(PlatformGame.class, qe);

        //ugly, but ok for now. Really more modelled to a SQL-DB, would not use dynamo for this data model in production
        return gamePlatforms.stream()
                .map(up -> mapper.load(Platform.class, up.getPlatformId()))
                .collect(Collectors.toList());
    }

    public List<Platform> getPlatformsForUser(String userId) {
        UserPlatform userPlatform = new UserPlatform();
        userPlatform.setUserId(userId);

        DynamoDBQueryExpression<UserPlatform> qe = new DynamoDBQueryExpression<UserPlatform>()
                .withHashKeyValues(userPlatform)
                .withConsistentRead(false);

        PaginatedQueryList<UserPlatform> platformUsers = mapper.query(UserPlatform.class, qe);

        //ugly, but ok for now. Really more modelled to a SQL-DB, would not use dynamo for this data model in production
        return platformUsers.stream()
                .map(up -> mapper.load(Platform.class, up.getPlatformId()))
                .collect(Collectors.toList());
    }

    public Platform save(Platform platform) {
        mapper.save(platform);
        return platform;
    }

    public String update(Platform platform) {
        mapper.save(platform);

        return "Successfully Updated Folder: " + platform.toString();
    }

    // Delete
    public String delete(String platformId) {
        Platform platform = mapper.load(Platform.class, platformId);
        mapper.delete(platform);
        return "Successfully deleted Contact: " + platform.toString();
    }
}
