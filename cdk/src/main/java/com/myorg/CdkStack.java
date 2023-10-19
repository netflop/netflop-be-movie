package com.myorg;

import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.Duration;

import java.text.MessageFormat;

public class CdkStack extends Stack {
    public CdkStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CdkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        String env = (String)this.getNode().tryGetContext("env");

        if(env == null || env.isEmpty()) {
            env = "dev";
        }

        final var lambda = Function.Builder
                .create(this, "netflop-movie-lambda")
                .runtime(Runtime.JAVA_11)
                .handler("com.netflop.be.movie.LambdaHandler")
                .code(Code.fromAsset("../build/distributions/handler.zip"))
                .timeout(Duration.seconds(30))
                .functionName(MessageFormat.format("netflop-movie-{0}", env))
                .build();

        final var api = LambdaRestApi.Builder.create(this, "netflop-movie-restapi")
                .restApiName(MessageFormat.format("netflop-movie-api-{0}", env))
                .handler(lambda)
                .build();
    }
}
