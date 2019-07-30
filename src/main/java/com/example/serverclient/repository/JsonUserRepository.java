package com.example.serverclient.repository;

import com.example.serverclient.io.JsonReader;
import com.example.serverclient.io.JsonWriter;
import com.example.serverclient.model.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JsonUserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUserRepository.class);

    private static final String TMP_EXTENSION = ".tmp";

    @Autowired
    JsonReader jsonReader;

    @Autowired
    JsonWriter jsonWriter;

    @Value("${repository.data.filepath}")
    private Path jsonFilepath;

    public boolean add(UserDTO userDTO) {
        Map<Integer, UserDTO> users = readUsers();
        if (users.get(userDTO.getId()) != null) {
            return false;
        }
        users.put(userDTO.getId(), userDTO);
        return save(users);
    }

    public UserDTO read(Integer id) {
        return readUsers().get(id);
    }

    public List<UserDTO> readAll() {
        return new ArrayList<>(readUsers().values());
    }

    public Map<Integer, UserDTO> readUsers() {
        try {
            Map<Integer, UserDTO> users = new HashMap<>();
            InputStream inputStream = Files.newInputStream(jsonFilepath);
            List<UserDTO> userList = jsonReader.read(inputStream, new TypeReference<List<UserDTO>>() {
            });

            for (UserDTO userDTO : userList) {
                users.put(userDTO.getId(), userDTO);
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse users", e);
        }
    }

    private boolean save(Map<Integer, UserDTO> users) {
        try {
            Path tempFile = jsonFilepath.getParent().resolve(jsonFilepath.getFileName().toString() + TMP_EXTENSION);
            OutputStream outputStream = Files.newOutputStream(tempFile);
            jsonWriter.write(outputStream, users.values());
            Files.move(tempFile, jsonFilepath, StandardCopyOption.ATOMIC_MOVE);
            return true;
        } catch (IOException e) {
            LOG.error("Failed to save users", e);
            return false;
        }
    }

    public boolean update(UserDTO userDTO) {
        Map<Integer, UserDTO> users = readUsers();

        if (users.get(userDTO.getId()) == null) {
            return false;
        }
        users.put(userDTO.getId(), userDTO);
        return save(users);
    }

    public boolean detele(UserDTO userDTO) {
        Map<Integer, UserDTO> users = readUsers();
        if (users.get(userDTO.getId()) == null) {
            return false;
        }
        users.remove(userDTO.getId());
        return save(users);
    }
}
