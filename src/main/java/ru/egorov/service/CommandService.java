package ru.egorov.service;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface CommandService {
    void execute(String inputFilePath, String outputFilePath) throws IOException, ParseException;
}
