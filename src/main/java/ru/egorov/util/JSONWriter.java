package ru.egorov.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.egorov.dto.Response;
import ru.egorov.exception.ReadJSONFileException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JSONWriter {

    public void write(String filePath, Response response) {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, false), StandardCharsets.UTF_8))) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(response, writer);
        } catch (IOException e) {
            throw new ReadJSONFileException(e.getMessage());
        }
    }
}
