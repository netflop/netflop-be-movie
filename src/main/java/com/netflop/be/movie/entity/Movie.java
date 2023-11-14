package com.netflop.be.movie.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;

@DynamoDbBean
@NoArgsConstructor
@Data
public class Movie {
    @Getter(onMethod_={@DynamoDbPartitionKey,@DynamoDbAttribute("movie_id")})
    private String id;

    @Getter(onMethod_=@DynamoDbAttribute("name"))
    private String name;

    @Getter(onMethod_=@DynamoDbAttribute("description"))
    private String description;

    @Getter(onMethod_=@DynamoDbAttribute("movie_url"))
    private String movieUrl;

    @Getter(onMethod_=@DynamoDbAttribute("trailer_url"))
    private String trailerUrl;

    @Getter(onMethod_=@DynamoDbAttribute("category"))
    private String category;

    @Getter(onMethod_=@DynamoDbAttribute("like"))
    private int like;

    @Getter(onMethod_=@DynamoDbAttribute("dislike"))
    private int dislike;

    @Getter(onMethod_=@DynamoDbAttribute("view"))
    private int view;

    @Getter(onMethod_=@DynamoDbAttribute("created_at"))
    private Instant createdAt;

    @Getter(onMethod_=@DynamoDbAttribute("updated_at"))
    private Instant updatedAt;

    @Getter(onMethod_=@DynamoDbAttribute("created_by"))
    private String createdBy;

    @Getter(onMethod_=@DynamoDbAttribute("updated_by"))
    private String updatedBy;

    @Getter(onMethod_=@DynamoDbAttribute("is_deleted"))
    private boolean isDeleted = false;
}
