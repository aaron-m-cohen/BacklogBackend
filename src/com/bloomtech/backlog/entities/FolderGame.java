package com.bloomtech.backlog.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "FoldersGames")
public class FolderGame {

    public static final String FOLDER_GAME_INDEX = "GamesFoldersIndex";

    @DynamoDBHashKey(attributeName = "folderId")
    @DynamoDBIndexRangeKey(attributeName = "folderId", globalSecondaryIndexName = "GamesFoldersIndex")
    private String folderId;


    @DynamoDBRangeKey(attributeName = "gameId")
    @DynamoDBIndexHashKey(attributeName = "gameId", globalSecondaryIndexName = "GamesFoldersIndex")
    private String gameId;

    @DynamoDBAttribute(attributeName = "notes")
    private String notes;
}
