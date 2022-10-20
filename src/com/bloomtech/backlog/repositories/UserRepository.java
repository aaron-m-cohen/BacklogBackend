package com.bloomtech.backlog.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.bloomtech.backlog.entities.Game;
import com.bloomtech.backlog.entities.User;
import com.bloomtech.backlog.entities.UserPlatform;
import com.bloomtech.backlog.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    @Autowired
    private DynamoDBMapper mapper;

    public User getUser(String userId) {
        User user = mapper.load(User.class, userId);

        if (user == null) {
            throw new UserNotFoundException("Could not find user for the provided userId. Please try again.");
        }
        return user;
    }

    public List<User> getUsersForPlatform (String platformId) {
        UserPlatform userPlatform = new UserPlatform();
        userPlatform.setPlatformId(platformId);

        DynamoDBQueryExpression<UserPlatform> qe = new DynamoDBQueryExpression<UserPlatform>()
                .withHashKeyValues(userPlatform)
                .withConsistentRead(false)
                .withIndexName(UserPlatform.PLATFORMS_USERS_INDEX);

        PaginatedQueryList<UserPlatform> platformUsers = mapper.query(UserPlatform.class, qe);

        //ugly, but ok for now. Really more modelled to a SQL-DB, would not use dynamo for this data model in production
        return platformUsers.stream().map(up -> mapper.load(User.class, up.getUserId())).collect(Collectors.toList());
    }

    public User save(User user) {
        mapper.save(user);
        return user;
    }

    public String update(User user) {
        mapper.save(user);

        return "Successfully Updated Folder: " + user.toString();
    }

    // Delete
    public String delete(String userId) {
        User user = mapper.load(User.class, userId);
        mapper.delete(user);
        return "Successfully deleted Contact: " + user.toString();
    }
}
