package ru.egorov.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import ru.egorov.util.JSONReader;

import java.io.IOException;

public class SearchCommandService implements CommandService {
    @Override
    public void execute(String inputFilePath, String outputFilePath) throws IOException, ParseException {
        JSONObject json = new JSONReader().readFile(inputFilePath);
    }
}
