package org.musicbotcom.commands.service;

import org.musicbotcom.commands.CommandResult;
import org.musicbotcom.commands.SingleStateCommand;
import org.musicbotcom.storage.User;

public class StartCommand implements SingleStateCommand {

  @Override
  public CommandResult react(String message, User user) {
    return CommandResult.returnToEmptyState("""
            Привет!
            Этот бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом
            Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию
        """);
  }
}
