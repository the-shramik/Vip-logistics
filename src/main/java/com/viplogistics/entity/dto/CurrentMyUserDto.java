package com.viplogistics.entity.dto;

import com.viplogistics.entity.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CurrentMyUserDto - Represents the currently authenticated user details.
 *
 * This DTO contains the username and associated role details of the authenticated user.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentMyUserDto {

    private String userName;

    private RoleDto roleDto;
}
