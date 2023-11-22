package org.musicbotcom.commands.playlist;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.User;

public class DeletePlaylist implements Command {
  private String message;

  @Override
  public Command react(String message, User user) {
    var playlist = user.getPlaylist(message);

    if (playlist.isEmpty()) {
      this.message = "Плейлист %s не существует, выберете другое имя!".formatted(message);

      return new DeletePlaylist();
    }

    user.playlists.remove(playlist.get());
    this.message = "Плейлист %s успешно удален".formatted(message);

    return new ProcessCommand();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
