package org.musicbotcom.commands.playlist;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.commands.tracks.AddTrack;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.User;

public class AddPlaylist implements Command {
  private String message;

  @Override
  public Command react(String message, User user) {
    var playlist = user.getPlaylist(message);

    if (playlist.isPresent()) {
      this.message = "Плейлист %s уже существует, выберете другое имя!".formatted(message);

      return new AddPlaylist();
    }

    user.playlists.add(new Playlist(message));

    this.message = "Плейлист %s создан".formatted(message);

    return new ProcessCommand();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
