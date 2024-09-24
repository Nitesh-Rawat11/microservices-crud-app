package com.user.service.services;

import java.util.List;

import com.user.service.entities.User;

public interface UserService {

    //create
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId
    User getUser(String userId);
    
    User updateUser(User user);
    
    void deleteUser(String id);


}
