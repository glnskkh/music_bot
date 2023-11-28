package org.musicbotcom.commands.playlist;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.User;

public class AddPlaylist implements Command {

  private String message;

  @Override
  public Command react(String playlistName, User user) {
    var playlist = user.getPlaylist(playlistName);

    if (playlist.isPresent()) {
      this.message = "Плейлист %s уже существует, выберете другое имя!".formatted(
          playlistName);

      return new AddPlaylist();
    }

    user.addPlaylist(playlistName);

    this.message = "Плейлист %s создан".formatted(playlistName);

    return new ProcessCommand();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
