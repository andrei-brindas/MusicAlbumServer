package com.example.serverclient.service;

import com.example.serverclient.model.UserDTO;
import com.example.serverclient.repository.JsonUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    JsonUserRepository jsonUserRepository;


    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = jsonUserRepository.readAll();
        if (users.isEmpty()) {
            return null;
        } else {
            return users;
        }
    }

    public void deleteUser(Integer id) {
        UserDTO userDTO = jsonUserRepository.read(id);
        jsonUserRepository.detele(userDTO);

    }

    public void addUser(UserDTO userDTO) {
        jsonUserRepository.add(userDTO);
    }
}
