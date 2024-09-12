package enums;

public enum Commands {

    ECHO("echo"), PWD("pwd"), CD("cd"),
    EXIT("exit"), TYPE("type");

    private final String commandName;

    Commands(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
