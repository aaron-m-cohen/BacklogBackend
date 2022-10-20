package com.bloomtech.backlog.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "PlatformsGames")
public class PlatformGame {

    public static final String GAMES_PLATFORMS_INDEX = "GamesPlatformsIndex";

    @DynamoDBHashKey(attributeName = "platformId")
    @DynamoDBIndexRangeKey(attributeName = "platformId", globalSecondaryIndexName = "GamesPlatformsIndex")
    private String platformId;

    @DynamoDBRangeKey(attributeName = "gameId")
    @DynamoDBIndexHashKey(attributeName = "gameId", globalSecondaryIndexName = "GamesPlatformsIndex")
    private String gameId;

    @DynamoDBAttribute(attributeName = "releaseDate")
    private String releaseDate;

}
