package org.musicbotcom.commands.playlist;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.User;

public class AddPlaylist implements Command {
  private String message;

  @Override
  public Command react(String message, User user) {
    var playlist = user.getPlaylist(message);

    Command command = new ProcessCommand();

    if (playlist.isEmpty()) {
      user.playlists.add(new Playlist(message));

      this.message = "Плейлист %s создан".formatted(message);
    } else {
      command = new AddPlaylist();

      this.message = "Плейлист %s уже существует, выберете другое имя!".formatted(message);
    }

    return command;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
