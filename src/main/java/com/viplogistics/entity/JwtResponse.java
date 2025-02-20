package com.viplogistics.entity;

import com.viplogistics.entity.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * {@code JwtResponse} - Represents the response sent to the user after successful authentication.
 *
 * This class contains the necessary information returned after a user is authenticated
 * and a JWT (JSON Web Token) is issued. It includes the user's username, the generated JWT token,
 * the user's role, and the image URL associated with the user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String username;
    private String jwtToken;
    private RoleDto role;
    private String imageUrl;
}
