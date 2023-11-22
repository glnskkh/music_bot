package org.musicbotcom.commands.playlist;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.User;

public class ShowPlaylist implements Command {
  private String message;

  @Override
  public Command react(String message, User user) {
    var playlist = user.getPlaylist(message);

    Command command = new ProcessCommand();

    if (playlist.isPresent()) {
      this.message = playlist.get().show();
    } else {
      command = new ShowPlaylist();

      this.message = "Плейлист %s не существует, выберете другое имя!".formatted(message);
    }

    return command;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
