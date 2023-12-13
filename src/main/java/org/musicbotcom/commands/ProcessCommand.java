package org.musicbotcom.commands;

import org.musicbotcom.commands.playlist.AddPlaylist;
import org.musicbotcom.commands.playlist.DeletePlaylist;
import org.musicbotcom.commands.playlist.ShowPlaylist;
import org.musicbotcom.commands.tracks.AddTrack;
import org.musicbotcom.storage.User;

public class ProcessCommand implements SingleStateCommand {

  @Override
  public CommandResult react(String userInput, User user) {
    return switch (userInput) {
      case "/addPlaylist" ->
          CommandResult.invocation(new AddPlaylist(), userInput, user);
      case "/deletePlaylist" ->
          CommandResult.invocation(new DeletePlaylist(), userInput, user);
      case "/showPlaylist" ->
          CommandResult.invocation(new ShowPlaylist(), userInput, user);
      case "/addTrack" ->
          CommandResult.invocation(new AddTrack(), userInput, user);

      case "/start" -> {
        var message = """
                Привет!
                Этоn бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом
                Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию
                Для привязки своего профиля ВК, отправьте боту <сслыка на бота в вк> код: <копируемое сообщение>
            """;

        yield CommandResult.returnToEmptyState(message);
      }
      case "/help" -> {
        var message = """
            Этот бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом.
            Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию
            Перечень команд:
            <...>
            <...>
            <...>""";
        yield CommandResult.returnToEmptyState(message);
      }
      case "/authorization" -> {
        var message = "Бот отправит вам идентификатор для авторизации в боте ВКонтакте и привязки аккаунтов";
        yield CommandResult.returnToEmptyState(message);
      }
      case "/me" -> {
        var message = String.valueOf(user.getChatId());
        yield CommandResult.returnToEmptyState(message);
      }

      default -> {
        var message = "Бот не знает такой команды, попробуйте ввести другую команду,\nДоступные команды: <...>";
        yield new CommandResult(new ProcessCommand(), message);
      }
    };
  }
}
