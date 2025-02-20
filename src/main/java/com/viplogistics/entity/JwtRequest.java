package com.viplogistics.entity;

import lombok.Data;

/**
 * {@code JwtRequest} - Represents the request sent by a user for authentication
 * using JWT (JSON Web Token).
 *
 * This class contains the username and password provided by the user for login
 * and is used during the authentication process in the security flow.
 */
@Data
public class JwtRequest {
    private String username;
    private String password;
}