package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface IDataBaseService {

    ApiResponse<?> resetDatabase();

}
