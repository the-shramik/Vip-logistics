package com.viplogistics.service.impl;

import com.viplogistics.controller.JwtAuthenticationController;
import com.viplogistics.entity.ApiResponse;
import com.viplogistics.service.IDataBaseService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DataBaseServiceImpl implements IDataBaseService {

    private final EntityManager entityManager;

    private final JwtAuthenticationController jwtAuthenticationController;

    @Transactional
    @Override
    public ApiResponse<?> resetDatabase() {

        try {
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();

            entityManager.createNativeQuery("SHOW TABLES").getResultList()
                    .forEach(table -> {
                        entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
                    });

            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();

            jwtAuthenticationController.createAdmin();

            return new ApiResponse<>(true, "Data reset successfully", null, HttpStatus.OK);
        }
        catch (Exception e){
            return new ApiResponse<>(false, "Data not reset", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
