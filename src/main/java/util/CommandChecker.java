package util;

import enums.Commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CommandChecker {

    public static boolean isValidCommand(String input) {
        return Arrays.stream(Commands.values())
                .anyMatch(command -> command.getCommandName().equals(input));
    }

    public static void terminateProgram() {
        System.exit(0);
    }

    public static void printInput(String input) {
        System.out.println(input);
    }

    public static void printBuiltIn(String input) {
        if (isValidCommand(input.substring(5))) {
            System.out.println(input.substring(5) + " is a shell builtin");
        } else {
            System.out.println(input.substring(5) + ": not found");
        }
    }

    public static boolean findCommandInPath(String command, String path) {
        String[] directories = path.split(":");

        for (String directory : directories) {
            File file = new File(directory, command);
            if (file.exists() && file.canExecute()) {
                System.out.println(command + " is " + file.getAbsolutePath());
                return true;
            }
        }
        return false;
    }

    public static boolean isBuiltInCommand(String command) {
        return Arrays.stream(Commands.values())
                .anyMatch(cmd -> cmd.getCommandName().equals(command));
    }

    public static void executeExternalCommand(String[] commandWithArgs) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(commandWithArgs);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            System.out.println("Error: Process exited with code " + exitCode);
        }
    }

    public static boolean findExternalCommandInPath(String command, String path) {
        String[] directories = path.split(":");

        for (String directory : directories) {
            File file = new File(directory, command);
            if (file.exists() && file.canExecute()) {
                return true;
            }
        }
        return false;
    }
}
