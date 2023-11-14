package com.netflop.be.movie.dao;

import com.netflop.be.movie.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;

@Slf4j
@Repository
public class UserDao implements IDao<User> {
    private final DynamoDbTable<User> table;

    @Autowired
    public UserDao(DynamoDbEnhancedClient dbClient, @Value("${DYNAMODB_USER_TABLE}") String tableName) {
        this.table = dbClient.table(tableName, TableSchema.fromBean(User.class));
    }

    @Override
    public User insert(User user) {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    public User findByEmail(String email) {
        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                .filterExpression(Expression.builder()
                                          .expression("#email = :v_email AND #is_deleted = :v_isDeleted")
                                          .putExpressionName("#email", "email")
                                          .putExpressionValue(":v_email", AttributeValue.builder()
                                                  .s(email)
                                                  .build())
                                          .putExpressionName("#is_deleted", "is_deleted")
                                          .putExpressionValue(":v_isDeleted", AttributeValue.builder()
                                                  .bool(false)
                                                  .build())
                                          .build())
                .build();

        List<User> listUser = this.table.scan(scanRequest)
                .stream()
                .toList()
                .get(0)
                .items();

        if (listUser.isEmpty()) {
            return null;
        }

        return listUser.get(0);

//        ScanRequest scanReq = ScanRequest.builder()
//                .tableName("netflop-user-dev")
//                .filterExpression("#email = :v_email AND #is_deleted = :v_isDeleted")
//                .expressionAttributeNames(Map.of("#email", "email", "#is_deleted", "is_deleted"))
//                .expressionAttributeValues(Map.of(":v_email", AttributeValue.builder()
//                                                  .s(email)
//                                                  .build(),
//                                                  ":v_isDeleted", AttributeValue.builder()
//                                                  .bool(false)
//                                                  .build()))
//                .build();
//
//        var response = dynamoDbClient.scan(scanReq).items().stream().toList().get(0);
//
//        User user = new User();
//        user.setId(response.get("user_id").s());
//        user.setEmail(response.get("email").s());
//        user.setDeleted(response.get("is_deleted").bool());
//        user.setType(response.get("type").s());
//        user.setCreatedBy(response.get("created_by").s());
//        user.setCreatedAt(response.get("created_at").s());
//        user.setStatus(response.get("status").s());
//        user.setFirstName(response.get("first_name").s());
//        user.setLastName(response.get("last_name").s());
//        user.setPhoneNumber(response.get("phone_number").s());
//        user.setUpdatedAt(response.get("updated_at").s());
//        user.setUpdatedBy(response.get("updated_by").s());
//
//        return user;
    }

    @Override
    public User delete(String id) {
        return null;
    }
}
