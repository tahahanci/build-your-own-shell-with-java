# Command Line Shell Project

This project is a simple command line shell implemented in Java. It supports executing both built-in commands and external programs, similar to how a traditional shell like Bash works.

## Features

- **Built-in Commands**: The shell supports a set of built-in commands such as `echo`, `pwd`, `cd`, `exit`, and `type`.
- **Executing External Programs**: The shell can execute programs found in the system's `PATH` environment variable with arguments.
- **Command Type Identification**: The `type` command can identify whether a command is built-in or external.

## Built-in Commands

The following built-in commands are supported:

- `echo`: Prints the input text to the console.
  ```bash
  $ echo Hello World
  Hello World