package ru.egorov;

import com.google.gson.JsonObject;
import ru.egorov.config.ApplicationConfig;
import ru.egorov.dto.Response;
import ru.egorov.exception.InvalidArgumentException;
import ru.egorov.exception.ReadJSONFileException;
import ru.egorov.exception.WriteJSONFileException;
import ru.egorov.service.CommandService;
import ru.egorov.service.SearchCommandService;
import ru.egorov.service.StatCommandService;
import ru.egorov.util.JSONReader;
import ru.egorov.util.JSONWriter;

import java.util.Optional;

public class CommandLineRunner {
    public static void run(String[] args) {
        try {
            ApplicationConfig.loadProperties();
            if (args.length != 3) {
                throw new InvalidArgumentException("Неправильное количество аргументов.");
            }

            String command = args[0];
            String inputFilePath = args[1];
            String outputFilePath = args[2];

            CommandService commandService = getCommandService(command)
                    .orElseThrow(() -> new InvalidArgumentException("Неизвестная команда: " + command));

            checkFilePaths(inputFilePath, outputFilePath);

            JSONReader reader = new JSONReader();
            JsonObject json = reader.read(inputFilePath);
            Response response = commandService.execute(json);

            JSONWriter writer = new JSONWriter();
            writer.write(outputFilePath, response);
        } catch (WriteJSONFileException e) {
            System.err.println("Ошибка записи в выходной JSON-файл. Подробнее: " + e.getMessage());
        } catch (ReadJSONFileException e) {
            System.err.println("Ошибка чтения входящего JSON-файла. Подробнее: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private static Optional<CommandService> getCommandService(String command) {
        switch (command) {
            case "search":
                return Optional.of(new SearchCommandService());
            case "stat":
                return Optional.of(new StatCommandService());
            default:
                return Optional.empty();
        }
    }

    private static void checkFilePaths(String inputFilePath, String outputFilePath) throws InvalidArgumentException {
        if (!inputFilePath.endsWith(".json")) {
            throw new InvalidArgumentException("Неверный формат входного файла.");
        } else if (!outputFilePath.endsWith(".json")) {
            throw new InvalidArgumentException("Неверный формат выходного файла.");
        }
    }
}
