package com.viplogistics.controller;

import com.viplogistics.entity.*;
import com.viplogistics.entity.dto.PermissionDto;
import com.viplogistics.entity.dto.RoleDto;
import com.viplogistics.exception.UserAlreadyPresentException;
import com.viplogistics.jwt.JwtUtils;
import com.viplogistics.repository.IMyUserRepository;
import com.viplogistics.repository.IPermissionRepository;
import com.viplogistics.repository.IRoleRepository;
import com.viplogistics.service.IMyUserService;
import com.viplogistics.service.IRoleService;
import com.viplogistics.service.impl.jwt.MyUserDetailsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * JwtAuthenticationController - Handles API requests for managing security.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
@CrossOrigin("*")
public class JwtAuthenticationController {

    private final MyUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final IMyUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final IPermissionRepository permissionRepository;

    private final IRoleService roleService;

    private final IMyUserService userService;

    private final S3Client s3Client;

    /**
     * Initializes the system by creating a default SUPER_ADMIN user if it does not exist.
     * This method runs after the Spring context is initialized.
     *
     * @throws UserAlreadyPresentException if the user already exists.
     * @throws IOException if an error occurs while reading the default profile image.
     */
    @PostConstruct
    public void createAdmin() throws UserAlreadyPresentException, IOException {
        Optional<MyUser> optionalUser = userRepository.getMyUserByUsername("headofficeshiroli@viplogistics.org");
        if (optionalUser.isEmpty()) {

            Optional<Role> optionalRole = roleRepository.findByRoleName("SUPER_ADMIN");

            Role savedRole = roleRepository.findByRoleName("SUPER_ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setRoleName("SUPER_ADMIN");
                        role.setRoleDescription("This is super admin role");
                        return roleRepository.save(role);
                    });

            if (permissionRepository.getPermissionsByRole(savedRole).isEmpty()) {
                Privilege privilege = new Privilege();
                privilege.setWritePermission("WRITE");
                privilege.setReadPermission("READ");
                privilege.setDeletePermission("DELETE");
                privilege.setUpdatePermission("UPDATE");

                Permissions permissions = new Permissions();
                permissions.setUserPermission("ALL_PERMISSIONS");
                permissions.setRole(savedRole);
                permissions.setPrivilege(privilege);
                roleService.createPermissions(List.of(permissions));
            }

            MyUser user = new MyUser();
            user.setName("Head Office");
            user.setUsername("headofficeshiroli@viplogistics.org");
            user.setAadhar("-");
            user.setAddress("PLOT NO 133, AMBEDKAR NAGAR, NAGAON PHATA, TAL. HATAKANGALE, DIST. KOLHAPUR - 416122");
            user.setBirthDate(LocalDate.now().toString());
            user.setContact("-");
            user.setPan("-");
            user.setRole(savedRole);
            user.setPassword(new BCryptPasswordEncoder().encode("vipheadoffice@2025"));

            String imagePath = "/static/superadmin.png";
            ClassPathResource imgFile = new ClassPathResource(imagePath);
            byte[] imageBytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

            String imageName = UUID.randomUUID().toString() + "super_admin.jpg";

            String bucketName = "springboot-test-0076";
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageName)
                    .contentType("image/jpeg")
                    .build();

            s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(imageBytes));

            String imageUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, imageName);

            user.setImageUrl(imageUrl);
            userRepository.save(user);
        }
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param request The login request containing username and password.
     * @return A {@link JwtResponse} containing the JWT token, user role, permissions, and profile details.
     * @throws BadCredentialsException if the provided credentials are incorrect.
     */
    @PostMapping("/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password.");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        System.out.println(userDetails);
        Optional<MyUser> optionalUser = userRepository.getMyUserByUsername(userDetails.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());
        JwtResponse response = new JwtResponse();
        if (optionalUser.isPresent()) {
            response.setJwtToken(jwt);
            Role role= optionalUser.get().getRole();

            RoleDto roleDto=new RoleDto();

            roleDto.setRoleId(role.getId());
            roleDto.setRoleName(role.getRoleName());
            roleDto.setRoleDescription(role.getRoleDescription());

            List<PermissionDto> permissionDtos=new ArrayList<>();
            for(Permissions permission:role.getPermissions()){
                PermissionDto permissionDto=new PermissionDto();
                List<String> privileges=new ArrayList<>();
                permissionDto.setUserPermission(permission.getUserPermission());
                privileges.add(permission.getPrivilege().getReadPermission());
                privileges.add(permission.getPrivilege().getWritePermission());
                privileges.add(permission.getPrivilege().getUpdatePermission());
                privileges.add(permission.getPrivilege().getDeletePermission());

                permissionDto.setPrivileges(privileges);
                permissionDtos.add(permissionDto);
            }

            roleDto.setPermissions(permissionDtos);

            response.setRole(roleDto);
            MyUser user = optionalUser.get();
            response.setUsername(user.getUsername());
            response.setImageUrl(user.getImageUrl());
            return response;
        }
        return null;
    }

    /**
     * Checks whether the provided JWT token has expired.
     *
     * @param jwtResponse The request containing the JWT token.
     * @return A {@link ResponseEntity} containing a boolean value:
     *         - {@code true} if the token is expired.
     *         - {@code false} if the token is still valid.
     *         If an exception occurs, it defaults to {@code true}.
     */
    @PostMapping("/is-token-expired")
    public ResponseEntity<?> idTokenExpired(@RequestBody JwtResponse jwtResponse) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(jwtUtils.isTokenExpired(jwtResponse.getJwtToken()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }


}
