package ru.egorov.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JSONWriter {

    public void write(String filePath, ru.egorov.dto.Response response) {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, false), StandardCharsets.UTF_8))) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(response, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
