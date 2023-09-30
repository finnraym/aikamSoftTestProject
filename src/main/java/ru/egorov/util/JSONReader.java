package ru.egorov.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSONReader {
    public JSONObject readFile(String filePath) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(filePath));
        return (JSONObject) obj;
    }
}
