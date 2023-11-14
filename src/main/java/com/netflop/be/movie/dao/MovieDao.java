package com.netflop.be.movie.dao;

import com.netflop.be.movie.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MovieDao implements IDao<Movie> {
    private final DynamoDbTable<Movie> table;

    @Autowired
    public MovieDao(DynamoDbEnhancedClient dbClient, @Value("${DYNAMODB_MOVIE_TABLE}") String tableName) {
        this.table = dbClient.table(tableName, TableSchema.fromBean(Movie.class));
    }

    @Override
    public Movie insert(Movie movie) {
        this.table.putItem(movie);
        return movie;
    }

    @Override
    public Movie findById(String id) {
        return this.table.getItem(Key.builder()
                                          .partitionValue(id)
                                          .build());
    }

    public Movie update(Movie movie) {
        return this.table.updateItem(movie);
    }

    @Override
    public Movie delete(String id) {
        return this.table.deleteItem(Key.builder()
                                             .partitionValue(id)
                                             .build());
    }

    public List<Movie> find(String keyWord, String category, int offset, int limit) {
        List<Movie> movies = new ArrayList<>();
        Map<String, AttributeValue> exclusiveStartKey = null;
        int numOfIgnoredItem = offset;
        List<String> expression = new ArrayList<>();

        if (keyWord != null) {
            expression.add("(contains(#name, v_keyWord) || contains(#description, v_keyWord))");
        }

        if (category != null) {
            expression.add("#category = v_category");
        }

        do {
            var scanRequest = ScanEnhancedRequest.builder()
                    .filterExpression(Expression.builder()
                                              .expression(String.join(" AND ", expression))
                                              .putExpressionName("#name", "name")
                                              .putExpressionName("#description", "description")
                                              .putExpressionValue("v_keyWord", AttributeValue.builder()
                                                      .s(keyWord)
                                                      .build())
                                              .putExpressionName("#category", "category")
                                              .putExpressionValue("v_category", AttributeValue.builder()
                                                      .s(category)
                                                      .build())
                                              .build())
                    .exclusiveStartKey(exclusiveStartKey)
                    .build();

            Page<Movie> scanResult = this.table.scan(scanRequest)
                    .stream()
                    .toList()
                    .get(0);

            exclusiveStartKey = scanResult.lastEvaluatedKey();

            int itemsScanSize = scanResult.items().size();

            if (numOfIgnoredItem == 0) {
                if (itemsScanSize + movies.size() <= limit) {
                    movies.addAll(scanResult.items());
                } else {
                    movies.addAll(scanResult.items().subList(0, itemsScanSize + movies.size() - limit));
                }
            }

            if (itemsScanSize <= numOfIgnoredItem) {
                numOfIgnoredItem -= itemsScanSize;
            } else {
                movies.addAll(scanResult.items().subList(numOfIgnoredItem, itemsScanSize));
                numOfIgnoredItem = 0;
            }

        } while (exclusiveStartKey != null && movies.size() < limit);

        return movies.stream()
                .sorted((m1, m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt())).toList();
    }
}
