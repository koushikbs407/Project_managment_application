package com.WorkWave.WorkWave.Service;

import com.WorkWave.WorkWave.Model.User;

public interface UserService {

    User findUserProfileByJWT(String jwt) throws Exception;
    User findUserProfileByEmail(String email) throws Exception;
    User findUserById(Long userId) throws Exception;
    User updateUsersProjectSize(User user,int number) throws Exception;



}
