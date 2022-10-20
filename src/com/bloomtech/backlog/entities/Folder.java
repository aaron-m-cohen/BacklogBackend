package com.bloomtech.backlog.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "Folders")
public class Folder {
    @DynamoDBHashKey(attributeName = "userId")
    private String userId;

    @DynamoDBRangeKey(attributeName = "folderId")
    @DynamoDBAutoGeneratedKey
    private String folderId;

    @DynamoDBAttribute(attributeName = "name")
    private String name;
}
