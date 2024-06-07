package com.project.ShopApp.services;

import com.project.ShopApp.dtos.UserDTO;
import com.project.ShopApp.models.User;
public interface IUserService {
    User register(UserDTO userDTO);

    String login(String phoneNumber, String password);
}
