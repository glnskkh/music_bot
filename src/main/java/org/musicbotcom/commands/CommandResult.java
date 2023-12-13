package org.musicbotcom.commands;

import org.musicbotcom.storage.User;

public record CommandResult(Command nextCommand, String message) {
  public static CommandResult returnToEmptyState(String message) {
    return new CommandResult(new ProcessCommand(), message);
  }

  public static CommandResult notChangeCommand(Command currentCommand, String message) {
    return new CommandResult(currentCommand, message);
  }

  public static CommandResult invocation(Command newCommand, String message, User user) {
    return new CommandResult(newCommand, newCommand.react(message, user).message());
  }
}
