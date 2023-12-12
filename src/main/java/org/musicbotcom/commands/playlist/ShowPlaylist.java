package org.musicbotcom.commands.playlist;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.User;

public class ShowPlaylist implements Command {

  private String message;

  @Override
  public Command react(String message, User user) {
    var playlist = user.getPlaylist(message);

    if (playlist.isEmpty()) {
      this.message = "Плейлист %s не существует, выберете другое имя!".formatted(
          message);

      return new ShowPlaylist();
    }

    this.message = user.showPlaylist(playlist.get());

    return new ProcessCommand();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
