package com.example.serverclient.controller;

import com.example.serverclient.model.UserDTO;
import com.example.serverclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class DataController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/data", produces = "application/json")
    @ResponseBody
    public List<UserDTO> getDataFromJson() throws IOException {
        List<UserDTO> users = userService.getAllUsers();
        if (users == null) {
            throw new IOException("Bad request");
        }
        return users;
    }

    @PostMapping(value = "/data/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/data/add", method = POST, produces = "application/json")
    @ResponseBody
    public boolean addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return true;
    }
}
