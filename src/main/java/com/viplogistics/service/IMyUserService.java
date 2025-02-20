package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.MyUser;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.exception.UserAlreadyPresentException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IMyUserService {

    boolean getMyUserByUserName(String userName);
    MyUser getMyUserByUserNameInfo(String userName);

    MyUser registerUser(MyUser user, MultipartFile file) throws UserAlreadyPresentException, IOException;

    List<MyUser> getAllUsers();

    MyUser updateUser(MyUser myUser,MultipartFile file) throws ResourceNotFoundException, IOException;

    ApiResponse<?> deleteMyUser(Long userId) throws ResourceNotFoundException;

    Map<String,Long> getEmployeeCount();

    MyUser getUserByUserName(String userName) throws ResourceNotFoundException;

    boolean updatePassword(String email, String newPassword);
}
