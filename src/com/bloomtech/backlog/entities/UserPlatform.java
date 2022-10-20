package com.bloomtech.backlog.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "UsersPlatforms")
public class UserPlatform {
    public static final String PLATFORMS_USERS_INDEX = "PlatformsUsersIndex";

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexRangeKey(attributeName = "userId", globalSecondaryIndexName = "PlatformsUsersIndex")
    private String userId;

    @DynamoDBRangeKey(attributeName = "platformId")
    @DynamoDBIndexHashKey(attributeName = "platformId", globalSecondaryIndexName = "PlatformsUsersIndex")
    private String platformId;
}
