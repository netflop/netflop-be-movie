package com.netflop.be.movie.entity;

import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;

@DynamoDbBean
public class Movie {
    @Getter(onMethod_=@DynamoDbPartitionKey)
    private String id;

    @Getter(onMethod_=@DynamoDbAttribute("name"))
    private String name;

    @Getter(onMethod_=@DynamoDbAttribute("created_date"))
    private Instant createdDate;

    @Getter(onMethod_=@DynamoDbAttribute("updated_date"))
    private Instant updatedDate;
}
