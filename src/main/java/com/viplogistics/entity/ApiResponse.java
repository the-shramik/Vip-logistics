package com.viplogistics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * {@code ApiResponse} - A generic wrapper class for API responses.
 *
 * This class is used to standardize the structure of API responses in the application.
 * It contains fields for success status, a message, data, and HTTP status code.
 *
 * @param <T> the type of the data returned in the response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private HttpStatus status;
}