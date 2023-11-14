package com.netflop.be.movie.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;

@DynamoDbBean
@NoArgsConstructor
@Setter
public class User {
    @Getter(onMethod_={@DynamoDbPartitionKey,@DynamoDbAttribute("user_id")})
    private String id;

    @Getter(onMethod_={@DynamoDbAttribute("email")})
    private String email;

    @Getter(onMethod_={@DynamoDbAttribute("first_name")})
    private String firstName;

    @Getter(onMethod_={@DynamoDbAttribute("last_name")})
    private String lastName;

    @Getter(onMethod_={@DynamoDbAttribute("phone_number")})
    private String phoneNumber;

    @Getter(onMethod_={@DynamoDbAttribute("status")})
    private String status;

    @Getter(onMethod_={@DynamoDbAttribute("type")})
    private String type;

    @Getter(onMethod_={@DynamoDbAttribute("created_at")})
    private String createdAt;

    @Getter(onMethod_={@DynamoDbAttribute("updated_at")})
    private String updatedAt;

    @Getter(onMethod_={@DynamoDbAttribute("created_by")})
    private String createdBy;

    @Getter(onMethod_={@DynamoDbAttribute("updated_by")})
    private String updatedBy;

    @Getter(onMethod_={@DynamoDbAttribute("is_deleted")})
    private boolean isDeleted;
}
