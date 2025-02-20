package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.MyUser;
import com.viplogistics.entity.Permissions;
import com.viplogistics.entity.Role;
import com.viplogistics.entity.dto.CurrentMyUserDto;
import com.viplogistics.entity.dto.PermissionDto;
import com.viplogistics.entity.dto.RoleDto;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.exception.UserAlreadyPresentException;
import com.viplogistics.service.IMyUserService;
import com.viplogistics.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserManagementController - Handles API requests for user managements.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-management")
@CrossOrigin("*")
public class UserManagementController {

    private final IMyUserService userService;

    private final IRoleService roleService;


    /**
     * Registers a new user.
     *
     * @param name      The name of the user.
     * @param username  The username of the user.
     * @param contact   The contact number of the user.
     * @param birthDate The birth date of the user.
     * @param pan       The PAN card number.
     * @param aadhar    The Aadhar card number.
     * @param address   The address of the user.
     * @param password  The user's password.
     * @param roleId    The role ID assigned to the user.
     * @param file      (Optional) The profile image of the user.
     * @return {@link ResponseEntity} with created user details or error message.
     * @throws UserAlreadyPresentException If the username already exists.
     * @throws IOException If an error occurs while processing the file.
     */
    @PostMapping("/register-user")
    public ResponseEntity<?> createNewCustomer(@RequestParam String name,
                                               @RequestParam String username,
                                               @RequestParam String contact,
                                               @RequestParam String birthDate,
                                               @RequestParam String pan,
                                               @RequestParam String aadhar,
                                               @RequestParam String address,
                                               @RequestParam String password,
                                               @RequestParam Long roleId,
                                               @RequestParam(value = "file",required = false) MultipartFile file

                                               ) throws UserAlreadyPresentException, IOException {
        if (userService.getMyUserByUserName(username)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Username already exists. Try again with a new username.");
        }

        MyUser myUser=new MyUser();
        myUser.setName(name);
        myUser.setUsername(username);
        myUser.setContact(contact);
        myUser.setBirthDate(birthDate);
        myUser.setPan(pan);
        myUser.setAadhar(aadhar);
        myUser.setAddress(address);
        myUser.setRole(roleService.findById(roleId));

        myUser.setPassword(new BCryptPasswordEncoder().encode(password));
        MyUser user = userService.registerUser(myUser,file);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Creates a new role.
     *
     * @param roleDto The role details.
     * @return {@link ResponseEntity} with the created role.
     */
    @PostMapping("/create-role")
    public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto) {
        Role createdRole = roleService.createRole(roleDto);
        return ResponseEntity.ok(createdRole);
    }

    /**
     * Retrieves all roles.
     *
     * @return {@link ResponseEntity} containing a list of all roles.
     */
    @GetMapping("/get-all-roles")
    public ResponseEntity<?> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    /**
     * Retrieves a role by its name.
     *
     * @param roleName The name of the role.
     * @return {@link ResponseEntity} containing role details.
     */
    @GetMapping("/get-role-by-role-name")
    public ResponseEntity<?> getRoleByRoleName(@RequestParam String roleName)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleByRoleName(roleName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    /**
     * Updates an existing role.
     *
     * @param roleDto The updated role details.
     * @return {@link ResponseEntity} containing updated role details.
     * @throws ResourceNotFoundException If the role is not found.
     */
    @PutMapping("/update-role")
    public ResponseEntity<?> updateRole(@RequestBody RoleDto roleDto) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(roleDto));
    }

    /**
     * Deletes a role.
     *
     * @param roleId The ID of the role to be deleted.
     * @return {@link ResponseEntity} containing success or failure response.
     */
    @DeleteMapping("/delete-role")
    public ResponseEntity<?> deleteRole(@RequestParam Long roleId){
        ApiResponse<?> response = roleService.deleteRole(roleId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Retrieves all users.
     *
     * @return {@link ResponseEntity} containing a list of all users.
     */
    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    /**
     * Updates an existing user's details.
     *
     * @param name      The name of the user.
     * @param username  The username of the user.
     * @param contact   The contact number of the user.
     * @param birthDate The birth date of the user.
     * @param pan       The PAN card number.
     * @param aadhar    The Aadhar card number.
     * @param address   The address of the user.
     * @param password  The user's password.
     * @param roleId    The role ID assigned to the user.
     * @param file      (Optional) The profile image of the user.
     * @return {@link ResponseEntity} with the updated route details.
     */
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestParam Long id,
                                        @RequestParam String name,
                                        @RequestParam String username,
                                        @RequestParam String contact,
                                        @RequestParam String birthDate,
                                        @RequestParam String pan,
                                        @RequestParam String aadhar,
                                        @RequestParam String address,
                                        @RequestParam String password,
                                        @RequestParam Long roleId,
                                        @RequestParam(value = "file",required = false) MultipartFile file){

        MyUser myUser=new MyUser();
        myUser.setId(id);
        myUser.setName(name);
        myUser.setUsername(username);
        myUser.setContact(contact);
        myUser.setBirthDate(birthDate);
        myUser.setPan(pan);
        myUser.setAadhar(aadhar);
        myUser.setAddress(address);
        myUser.setRole(roleService.findById(roleId));
        myUser.setPassword(password);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(myUser,file));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to be deleted.
     * @return {@link ResponseEntity} containing the success or failure response.
     * @throws ResourceNotFoundException If the user is not found.
     */
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Long id) throws ResourceNotFoundException {


        ApiResponse<?> response = userService.deleteMyUser(id);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Retrieves the current authenticated user's details.
     *
     * @param authentication The authenticated user context.
     * @return {@link ResponseEntity} containing the user's details.
     */
    @GetMapping("/current-user-details")
    public ResponseEntity<?> currentUserDetails(Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        UserDetails userDetails= (UserDetails) authentication.getPrincipal();

        MyUser user=userService.getMyUserByUserNameInfo(userDetails.getUsername());

        CurrentMyUserDto currentMyUserDto=new CurrentMyUserDto();

        currentMyUserDto.setUserName(user.getUsername());

        Role role=user.getRole();
        RoleDto roleDto=new RoleDto();

        roleDto.setRoleId(role.getId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setRoleDescription(role.getRoleDescription());

        List<PermissionDto> permissionDtos=new ArrayList<>();
        for(Permissions permissions:role.getPermissions()){
            PermissionDto permissionDto=new PermissionDto();

            permissionDto.setUserPermission(permissions.getUserPermission());
            List<String> privileges=new ArrayList<>();
            privileges.add(permissions.getPrivilege().getReadPermission());
            privileges.add(permissions.getPrivilege().getWritePermission());
            privileges.add(permissions.getPrivilege().getUpdatePermission());
            privileges.add(permissions.getPrivilege().getDeletePermission());

            permissionDto.setPrivileges(privileges);
            permissionDtos.add(permissionDto);
        }

        roleDto.setPermissions(permissionDtos);
        currentMyUserDto.setRoleDto(roleDto);
        return ResponseEntity.status(HttpStatus.OK).body(currentMyUserDto);
    }

    /**
     * Retrieves the total count of employees.
     *
     * @return {@link ResponseEntity} containing the employee count.
     */
    @GetMapping("/get-employee-count")
    public ResponseEntity<?> getEmployeeCount(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getEmployeeCount());
    }

    /**
     * Retrieves a user by its username.
     *
     * @param userName The username of the user.
     * @return {@link ResponseEntity} containing user details.
     */
    @GetMapping("/get-user-by-username")
    public ResponseEntity<?> getUserByUserName(@RequestParam String userName){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserName(userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
