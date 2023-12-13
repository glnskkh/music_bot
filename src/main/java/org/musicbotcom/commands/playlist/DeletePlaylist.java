package org.musicbotcom.commands.playlist;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.User;

public class DeletePlaylist implements Command {

  private String message;

  @Override
  public Command react(String playlistName, User user) {
    var playlist = user.getPlaylist(playlistName);

    if (playlist.isEmpty()) {
      this.message = "Плейлист %s не существует, выберете другое имя!".formatted(
          playlistName);

      return new DeletePlaylist();
    }

    user.removePlaylist(playlist.get());
    this.message = "Плейлист %s успешно удален".formatted(playlistName);

    return new ProcessCommand();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
