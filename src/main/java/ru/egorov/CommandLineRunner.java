package ru.egorov;

import org.json.simple.parser.ParseException;
import ru.egorov.exception.InvalidArgumentException;
import ru.egorov.service.CommandService;
import ru.egorov.service.SearchCommandService;
import ru.egorov.service.StatCommandService;

import java.io.IOException;
import java.util.Optional;

public class CommandLineRunner {
    public static void run(String[] args) {
        try {
            if (args.length != 3) {
                throw new InvalidArgumentException("Неправильное количество аргументов.");
            }

            String command = args[0];
            String inputFilePath = args[1];
            String outputFilePath = args[2];

            CommandService commandService = getCommandService(command)
                    .orElseThrow(() -> new InvalidArgumentException("Неизвестная команда."));

            checkFilePaths(inputFilePath, outputFilePath);

            commandService.execute(inputFilePath, outputFilePath);
        } catch (InvalidArgumentException | ParseException | IOException e) {
            System.out.println(e.getMessage());
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
