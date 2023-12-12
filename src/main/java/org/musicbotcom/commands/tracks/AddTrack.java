package org.musicbotcom.commands.tracks;

import java.util.List;
import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.Track;
import org.musicbotcom.storage.User;
import org.musicbotcom.storage.database.PlaylistDatabase;
import org.musicbotcom.storage.database.TracksDatabase;

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
    Track newTrack = new Track(0, trackName, "");

    List<Track> tracks = user.getTracks(playlist);

    if (tracks.contains(newTrack)) {
      this.message = "Трек уже добавлен %s в плейлист %s, введите другой".formatted(
          newTrack.name(), playlist.name());

      return this;
    }

    user.addTrack(playlist, newTrack);

    this.message = "Трек успешно добавлен %s в плейлист %s".formatted(
        newTrack.name(), playlist.name());

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
