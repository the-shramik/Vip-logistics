package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.MyUser;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.exception.UserAlreadyPresentException;
import com.viplogistics.repository.IMyUserRepository;
import com.viplogistics.service.IMyUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements IMyUserService {

    private final IMyUserRepository userRepository;

    private final S3Client s3Client;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean getMyUserByUserName(String userName) {
        return userRepository.getMyUserByUsername(userName).isPresent();
    }

    @Override
    public MyUser getMyUserByUserNameInfo(String userName) {
        return userRepository.getMyUserByUsername(userName).get();
    }

    @Override
    public MyUser registerUser(MyUser user,MultipartFile imageFile) throws UserAlreadyPresentException, IOException {

        if(userRepository.getMyUserByUsername(user.getUsername()).isPresent()){
            throw new  UserAlreadyPresentException("User already present with this email!");
        }

        if(imageFile!=null) {
            String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

            File file = convertMultipartFileToFile(imageFile);
            String bucketName = "springboot-test-0076";

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageName)
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromFile(file));

            file.delete();

            String imageUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, imageName);

            user.setImageUrl(imageUrl);
        }else {
            String imagePath = "/static/superadmin2.jpg";
            String bucketName = "springboot-test-0076";

            ClassPathResource imgFile = new ClassPathResource(imagePath);
            byte[] imageBytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

            String imageName = UUID.randomUUID().toString() + "super_admin.jpg";

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageName)
                    .contentType("image/jpeg")
                    .build();

            s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(imageBytes));

            String imageUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, imageName);

            user.setImageUrl(imageUrl);
        }

        return userRepository.save(user);
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    @Override
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public MyUser updateUser(MyUser myUser,MultipartFile imageFile) throws ResourceNotFoundException, IOException {
        MyUser existingUser=userRepository.findById(myUser.getId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id "+myUser.getId()));

        existingUser.setName(myUser.getName());
        existingUser.setPan(myUser.getPan());
        existingUser.setContact(myUser.getContact());
        existingUser.setRole(myUser.getRole());
        existingUser.setUsername(myUser.getUsername());

        if(myUser.getPassword()!=null && !myUser.getPassword().equals("")){
            existingUser.setPassword(new BCryptPasswordEncoder().encode(myUser.getPassword()));
        }else{
            existingUser.setPassword(existingUser.getPassword());
        }
        existingUser.setBirthDate(myUser.getBirthDate());
        existingUser.setAddress(myUser.getAddress());
        existingUser.setAadhar(myUser.getAadhar());

        if(imageFile!=null) {
            String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

            File file = convertMultipartFileToFile(imageFile);
            String bucketName = "springboot-test-0076";

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageName)
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

            file.delete();

            String imageUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, imageName);
            existingUser.setImageUrl(imageUrl);
        }
        return userRepository.save(existingUser);
    }

    @Override
    public ApiResponse<?> deleteMyUser(Long userId) throws ResourceNotFoundException {
        Optional<MyUser> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            MyUser user = optionalUser.get();

            if(user.getImageUrl()!=null) {
                String imageName = user.getImageUrl().substring(user.getImageUrl().lastIndexOf('/') + 1);
                s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket("springboot-test-0076")
                        .key(imageName)
                        .build());
            }

            userRepository.delete(user);
            return new ApiResponse<>(true,"User deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"User not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Map<String, Long> getEmployeeCount() {
        HashMap<String,Long> result=new HashMap<>();

        result.put("totalEmployees",userRepository.count());

        return result;
    }

    @Override
    public MyUser getUserByUserName(String userName) throws ResourceNotFoundException {
        return userRepository.getMyUserByUsername(userName)
                .orElseThrow(()->new ResourceNotFoundException("User not found with this username"));
    }

    @Transactional
    public boolean updatePassword(String email, String newPassword) {
        MyUser user = userRepository.getMyUserByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("New password must be different from the old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}
