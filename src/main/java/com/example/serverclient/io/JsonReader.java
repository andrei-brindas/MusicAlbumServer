package com.example.serverclient.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonReader {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T read(InputStream file, Class<T> type) throws IOException {
        return mapper.readValue(file, type);
    }

    public <T> T read(InputStream file, TypeReference<T> type) throws IOException {
        return mapper.readValue(file, type);
    }
}
