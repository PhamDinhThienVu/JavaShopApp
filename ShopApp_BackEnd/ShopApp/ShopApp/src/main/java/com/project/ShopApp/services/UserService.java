package com.project.ShopApp.services;

import com.project.ShopApp.dtos.UserDTO;
import com.project.ShopApp.models.Role;
import com.project.ShopApp.models.User;
import com.project.ShopApp.repositories.RoleRepository;
import com.project.ShopApp.repositories.UserRepository;
import com.project.ShopApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User register(UserDTO userDTO) {
        String phoneNumber = userDTO.getPhoneNumber();
        //Check if duplicate phone number
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found"));
        newUser.setRole(role);

        //
//        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() ==0){
//            String password = userDTO.getPassword();
//            String encodedPassword = passwordEncoder.endcode(password);
//            newUser.setPassword(encodedPassword);
//        }

        return  userRepository.save(newUser);

    }

    @Override
    public String login(String phoneNumber, String password) {
        //Security part


        return "";
    }
}
