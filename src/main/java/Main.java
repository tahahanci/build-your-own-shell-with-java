import enums.Commands;
import util.CommandChecker;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String currentDirectory = System.getProperty("user.dir");

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            String[] commandWithArgs = input.split(" ");
            String command = commandWithArgs[0];

            if (command.equals(Commands.EXIT.getCommandName())) {
                CommandChecker.terminateProgram();
            } else if (command.equals(Commands.ECHO.getCommandName())) {
                String message = input.substring(Commands.ECHO.getCommandName().length()).trim();
                CommandChecker.printInput(message);
            } else if (command.equals(Commands.CD.getCommandName())) {
                if (commandWithArgs.length > 1) {
                    currentDirectory = CommandChecker.changeDirectory(currentDirectory, commandWithArgs[1]);
                } else {
                    System.out.println("cd: No directory specified");
                }
            } else if (command.equals(Commands.PWD.getCommandName())) {
                String currentPath = CommandChecker.printWorkingDirectory(currentDirectory);
                System.out.println(currentPath);
            } else if (input.startsWith(Commands.TYPE.getCommandName())) {
                String typeCommand = input.substring(5);

                if (CommandChecker.isBuiltInCommand(typeCommand)) {
                    System.out.println(typeCommand + " is a shell builtin");
                } else {
                    String path = System.getenv("PATH");
                    if (!CommandChecker.findCommandInPath(typeCommand, path)) {
                        System.out.println(typeCommand + ": not found");
                    }
                }
            } else {
                String path = System.getenv("PATH");
                if (CommandChecker.findExternalCommandInPath(command, path)) {
                    try {
                        CommandChecker.executeExternalCommand(commandWithArgs, currentDirectory);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(input + ": command not found");
                }
            }
        }
    }
}
