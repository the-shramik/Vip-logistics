package com.viplogistics.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * S3Config - Configures the AWS S3 client for integration with the application.
 * This class initializes the {@link S3Client} bean with AWS credentials and region settings.
 * <p>
 * The credentials and region details are loaded from application properties.
 * </p>
 *
 * <p><strong>Dependencies:</strong></p>
 * - AWS SDK v2 for Java
 * - Spring Boot Configuration
 *
 * <p><strong>Configuration Properties:</strong></p>
 * - {@code cloud.aws.credentials.access-key} → AWS access key
 * - {@code cloud.aws.credentials.secret-key} → AWS secret key
 * - {@code cloud.aws.region.static} → AWS region
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * Creates and configures an AWS S3 client bean.
     * <p>
     * The S3 client is built using static AWS credentials and the specified region.
     * </p>
     *
     * @return {@link S3Client} - Configured AWS S3 client instance.
     */
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}