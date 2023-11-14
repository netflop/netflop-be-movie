package com.myorg;

import com.google.gson.Gson;
import com.myorg.dto.ConfigDTO;
import software.amazon.awscdk.services.apigateway.*;
import software.amazon.awscdk.services.cognito.UserPool;
import software.amazon.awscdk.services.iam.*;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SnapStartConf;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.Principal;
import java.text.MessageFormat;

import java.util.List;
import java.util.Map;

public class CdkStack extends Stack {
    public CdkStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CdkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        String env = (String) this.getNode()
                .tryGetContext("env");

        if (env == null || env.isEmpty()) {
            env = "dev";
        }

        BufferedReader br = null;
        String configFilePath = new File("").getAbsolutePath()
                .concat(MessageFormat.format("/config/config-{0}.json", env));
        try {
            br = new BufferedReader(new FileReader(configFilePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ConfigDTO config = new Gson().fromJson(br, ConfigDTO.class);

        final var lambdaRole = Role.Builder.create(this, "create-role")
                .roleName("movie-lambda-role-" + env)
                .assumedBy(ServicePrincipal.Builder.create("lambda.amazonaws.com").build())
                .build();

        final var policy = ManagedPolicy.Builder.create(this, "managed-policy")
                .managedPolicyName("movieLambdaPolicy")
                .statements(List.of(
                        PolicyStatement.Builder.create()
                                .actions(List.of("s3:*",
                                                 "dynamodb:*",
                                                 "logs:CreateLogGroup",
                                                 "logs:CreateLogStream",
                                                 "logs:PutLogEvents"))
                                .resources(List.of("*"))
                                .effect(Effect.ALLOW)
                                .build()))
                .build();

        policy.attachToRole(lambdaRole);

        final var lambda = Function.Builder
                .create(this, "netflop-movie-lambda")
                .runtime(Runtime.JAVA_17)
                .handler(config.getLambda().getHandler())
                .code(Code.fromAsset(config.getLambda().getCodePath()))
                .memorySize(512)
                .timeout(Duration.seconds(30))
                .functionName(config.getLambda().getName())
                .environment(config.getLambda().getEnv())
                .role(lambdaRole)
                .snapStart(SnapStartConf.ON_PUBLISHED_VERSIONS)
                .build();

        final var api = RestApi.Builder
                .create(this, "netflop-movie-restapi")
                .restApiName(config.getApi()
                                     .getName())
                .deployOptions(StageOptions.builder().stageName(config.getApi().getStage()).build())
                .build();

        final var authorizer = CognitoUserPoolsAuthorizer.Builder
                .create(this, "api-authorizer")
                .resultsCacheTtl(Duration.seconds(0))
                .authorizerName(MessageFormat.format("api-authorizer-{0}", env))
                .cognitoUserPools(List.of(UserPool.fromUserPoolId(this, "user-pool", config.getCognito().getCognitoPoolId())))
                .identitySource("method.request.header.authorization")
                .build();

        final var lambdaIntegration = LambdaIntegration.Builder
                .create(lambda)
                .build();

        api.getRoot()
                .addResource("{proxy+}")
                .addMethod("ANY", lambdaIntegration, MethodOptions.builder()
                        .authorizer(authorizer)
                        .authorizationType(AuthorizationType.COGNITO)
                        .build());

    }
}
