package org.musicbotcom.commands;

import org.musicbotcom.commands.playlist.AddPlaylist;
import org.musicbotcom.commands.playlist.DeletePlaylist;
import org.musicbotcom.commands.playlist.ShowPlaylist;
import org.musicbotcom.commands.tracks.AddTrack;
import org.musicbotcom.storage.User;

public class ProcessCommand implements Command {

  private String message;

  @Override
  public Command react(String message, User user) {

    return switch (message) {
      case "/start" -> {
        this.message = "Привет!\nЭто бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботомПомимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию\nДля привязки своего профиля ВК, отправьте боту <сслыка на бота в вк> код:<копируемое сообщение>";
        yield new ProcessCommand();
      }
      case "/help" -> {// TODO:
        this.message = "Это бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом.\n Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию\nПеречень команд:\n<...>\n<...>\n<...>";
        yield new ProcessCommand();
      }
      case "/authorization" -> {
        this.message = "Бот отправит вам идентификатор для авторизации в боте ВКонтакте и привязки аккаунтов";
        yield new ProcessCommand();
      }
      case "/me" -> {
        this.message = String.valueOf(user.getChatId());
        yield new ProcessCommand();
      }
      case "/addPlaylist" -> {
        this.message = "Введите название плейлиста";
        yield new AddPlaylist();
      }
      case "/deletePlaylist" -> {
        this.message = "Введите название плейлиста";
        yield new DeletePlaylist();
      }
      case "/showPlaylist" -> {
        this.message = "Введите название плейлиста";
        yield new ShowPlaylist();
      }
      case "/addTrack" -> {
        this.message = "Введите название плейлиста";
        yield new AddTrack();
      }
      default -> {
        this.message = "Бот не знает такой команды, попробуйте ввести другую команду,\nДоступные команды: <...>";
        yield new ProcessCommand();
      }
    };
  }

  @Override
  public String getMessage() {
    return message;
  }
}
