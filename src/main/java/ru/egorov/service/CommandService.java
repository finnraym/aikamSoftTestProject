package ru.egorov.service;

import com.google.gson.JsonObject;
import ru.egorov.dto.Response;
import ru.egorov.exception.WriteJSONFileException;

public interface CommandService {
    Response execute(JsonObject json) throws WriteJSONFileException;
}
