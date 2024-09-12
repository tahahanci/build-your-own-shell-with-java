import enums.Commands;
import util.CommandChecker;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            String[] commandWithArgs = input.split(" ");
            String command = commandWithArgs[0];

            if (input.startsWith(Commands.EXIT.getCommandName())) {
                CommandChecker.terminateProgram();
            } else if (input.startsWith(Commands.ECHO.getCommandName())) {
                CommandChecker.printInput(input.substring(Commands.ECHO.getCommandName().length() + 1));
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
            } else if (input.startsWith(Commands.PWD.getCommandName())) {
                String path = CommandChecker.printWorkingDirectory();
                System.out.println(path);
            } else {
                String path = System.getenv("PATH");
                if (CommandChecker.findExternalCommandInPath(command, path)) {
                    try {
                        CommandChecker.executeExternalCommand(commandWithArgs);
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
