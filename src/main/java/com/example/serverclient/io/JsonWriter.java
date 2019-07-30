package com.example.serverclient.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class JsonWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> void write(OutputStream outputStream, T content) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, content);

    }
}
