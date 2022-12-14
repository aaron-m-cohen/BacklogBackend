package com.bloomtech.backlog.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

/**
 * Represents a record in the Users table
 */
@Data
@DynamoDBTable(tableName = "Users")
public class User {

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAutoGeneratedKey
    private String userId;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "email")
    private String email;

}
