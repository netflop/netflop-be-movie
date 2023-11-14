package com.netflop.be.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.awscore.defaultsmode.DefaultsMode;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

import java.time.Duration;

@Configuration
public class AwsConfig {
    @Bean
    public DynamoDbClient dynamoDbClient () {
        return DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .defaultsMode(DefaultsMode.IN_REGION)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .httpClient(UrlConnectionHttpClient.builder().build())
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                                               .retryPolicy(RetryPolicy.builder().numRetries(0).additionalRetryConditionsAllowed(false).build())
                                               .apiCallTimeout(Duration.ofSeconds(15))
                                               .build())
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}
