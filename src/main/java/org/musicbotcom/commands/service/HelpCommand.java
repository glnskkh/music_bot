package org.musicbotcom.commands.service;

import org.musicbotcom.commands.CommandResult;
import org.musicbotcom.commands.SingleStateCommand;
import org.musicbotcom.storage.User;

public class HelpCommand implements SingleStateCommand {

  @Override
  public CommandResult react(String message, User user) {
    return CommandResult.returnToEmptyState("""
        Этот бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом.
        Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию
        Перечень команд:
        /start - Приветствие
        /help - Показать справку
        /addPlaylist - Создать новый плейлист
        /deletePlaylist - Удалить плэйлист
        /showPlaylists - Показать плейлисты
        /addTrack - Добавить трек в плейлист
        /showPlaylist - Показать содержимое плейлиста
        """);
  }
}
