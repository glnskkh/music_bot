package org.musicbotcom.commands.playlist;

import org.musicbotcom.commands.CommandResult;
import org.musicbotcom.commands.SingleStateCommand;
import org.musicbotcom.storage.User;

public class ShowPlaylist implements SingleStateCommand {

  private State state = State.Invocation;

  private CommandResult reactInvocation() {
    var message = "Введите название плейлиста";

    state = State.ReadPlaylistName;

    return CommandResult.notChangeCommand(this, message);
  }

  private CommandResult reactReadPlaylistName(String playlistName, User user) {
    if (!user.hasPlaylist(playlistName)) {
      var message = "Плейлист %s не существует, выберете другое имя!".formatted(
          playlistName);

      return CommandResult.notChangeCommand(this, message);
    }

    var message = user.showPlaylist(playlistName);

    if (message.isEmpty()) {
      message = "< Нет треков >";
    }

    return CommandResult.returnToEmptyState(message);
  }

  @Override
  public CommandResult react(String message, User user) {
    return switch (state) {
      case Invocation -> reactInvocation();
      case ReadPlaylistName -> reactReadPlaylistName(message, user);
    };
  }

  private enum State {
    Invocation, ReadPlaylistName,
  }
}
