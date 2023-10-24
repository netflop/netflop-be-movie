package com.netflop.be.movie.dao;

import com.netflop.be.movie.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;

@Component
public class MovieDao implements IDao<Movie> {
    private final DynamoDbTable<Movie> table;

    @Autowired
    public MovieDao(DynamoDbEnhancedClient dbClient) {
        String tableName = System.getenv("MOVIE_TABLE");
        this.table = dbClient.table(tableName, TableSchema.fromBean(Movie.class));
    }

    @Override
    public Movie save(Movie movie) {
        this.table.putItem(movie);
        return null;
    }

    @Override
    public Movie findById(String id) {
        return this.table.getItem(Key.builder().partitionValue(id).build());
    }

}
