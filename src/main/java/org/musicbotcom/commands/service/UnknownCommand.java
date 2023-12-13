package org.musicbotcom.commands.service;

import org.musicbotcom.commands.CommandResult;
import org.musicbotcom.commands.SingleStateCommand;
import org.musicbotcom.storage.User;

public class UnknownCommand implements SingleStateCommand {

  @Override
  public CommandResult react(String message, User user) {
    return CommandResult.returnToEmptyState(
        "Бот не знает такой комманды, воспользуйтесь /help");
  }
}
