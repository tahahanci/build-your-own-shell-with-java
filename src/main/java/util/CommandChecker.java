package util;

import enums.Commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public final class CommandChecker {

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
        String[] directories = path.split(File.pathSeparator);

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

    public static void executeExternalCommand(String[] commandWithArgs, String currentDirectory)
            throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(commandWithArgs);
        processBuilder.directory(new File(currentDirectory));
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            System.out.println("Error: Process exited with code " + exitCode);
        }
    }

    public static boolean findExternalCommandInPath(String command, String path) {
        String[] directories = path.split(File.pathSeparator);

        for (String directory : directories) {
            File file = new File(directory, command);
            if (file.exists() && file.canExecute()) {
                return true;
            }
        }
        return false;
    }

    public static String printWorkingDirectory(String currentDirectory) {
        return currentDirectory;
    }

    public static String changeDirectory(String currentDirectory, String path) {
        String homeDirectory = System.getenv("HOME");

        if (path.equals("~")) {
            path = homeDirectory;
        } else if (path.startsWith("~" + File.separator)) {
            path = homeDirectory + path.substring(1);
        }

        Path newPath = Paths.get(path);

        if (!newPath.isAbsolute()) {
            newPath = Paths.get(currentDirectory).resolve(newPath).normalize();
        }

        File newDir = newPath.toFile();
        if (newDir.exists() && newDir.isDirectory()) {
            return newDir.getAbsolutePath();
        } else {
            System.out.println("cd: " + path + ": No such file or directory");
            return currentDirectory;
        }
    }
}
