package com.viplogistics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * {@code Authority} - Represents a user's authority (role) for access control in the system.
 *
 * This class implements {@link GrantedAuthority} to provide the role information for Spring Security
 * and is used to define the authority of a user based on their assigned role.
 *
 * @see GrantedAuthority
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {
    private Role authority;

    @Override
    public String getAuthority() {
        return this.authority.getRoleName();
    }
}