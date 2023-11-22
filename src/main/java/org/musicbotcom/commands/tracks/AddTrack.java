package org.musicbotcom.commands.tracks;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.Track;
import org.musicbotcom.storage.User;

public class AddTrack implements Command {

  private Type type;
  private String message;
  private Playlist playlist;

  public AddTrack() {
    this.type = Type.EnterPlaylist;
  }

  private Command processEnterPlaylist(String playlistName, User user) {
    var playlist = user.getPlaylist(playlistName);

    if (playlist.isEmpty()) {
      this.message = "Плейлист %s не существует, выберете другое имя!".formatted(
          playlistName);

      return new AddTrack();
    }

    this.message = "Введи название трека";
    this.playlist = playlist.get();

    type = Type.EnterTrack;

    return this;
  }

  private Command processEnterTrack(String trackName, User user) {
    Track example = new Track(trackName, "");

    if (playlist.hasTrack(example)) {
      this.message = "Трек уже добавлен %s в плейлист %s, введите другой".formatted(
          example.show(), playlist.getName());

      return this;
    }

    playlist.addTrack(example);

    this.message = "Трек успешно добавлен %s в плейлист %s".formatted(
        example.show(), playlist.getName());

    return new ProcessCommand();
  }

  @Override
  public Command react(String message, User user) {
    return switch (type) {
      case EnterPlaylist -> processEnterPlaylist(message, user);
      case EnterTrack -> processEnterTrack(message, user);
    };
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public enum Type {
    EnterPlaylist, EnterTrack
  }
}
