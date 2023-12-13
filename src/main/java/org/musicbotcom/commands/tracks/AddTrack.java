package org.musicbotcom.commands.tracks;

import java.util.List;
import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.CommandResult;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.Track;
import org.musicbotcom.storage.User;

public class AddTrack implements Command {

  private States state = States.Invocation;
  private Playlist playlist;

  private CommandResult reactInvocation() {
    var message = "Введите название плейлиста";

    state = States.ReadPlaylistName;

    return CommandResult.notChangeCommand(this, message);
  }

  private CommandResult reactReadPlaylistName(String playlistName, User user) {
    var playlist = user.getPlaylist(playlistName);

    if (playlist.isEmpty()) {
      var message = "Плейлист %s не существует, выберете другое имя!".formatted(
          playlistName);

      return CommandResult.notChangeCommand(this, message);
    }

    this.playlist = playlist.get();

    var message = "Введи название трека";

    state = States.ReadTrackName;

    return CommandResult.notChangeCommand(this, message);
  }

  private CommandResult reactReadTrackName(String trackName, User user) {
    Track newTrack = new Track(0, trackName, "");

    List<Track> tracks = user.getTracks(playlist);

    if (Playlist.contains(tracks, newTrack)) {
      var message = "Трек уже добавлен %s в плейлист %s, введите другой".formatted(
          newTrack.name(), playlist.name());

      return CommandResult.notChangeCommand(this, message);
    }

    user.addTrack(playlist, newTrack);

    var message = "Трек успешно добавлен %s в плейлист %s".formatted(
        newTrack.name(), playlist.name());

    return CommandResult.returnToEmptyState(message);
  }

  @Override
  public CommandResult react(String message, User user) {
    return switch (state) {
      case Invocation -> reactInvocation();
      case States.ReadPlaylistName -> reactReadPlaylistName(message, user);
      case States.ReadTrackName -> reactReadTrackName(message, user);
    };
  }

  private enum States {
    Invocation, ReadPlaylistName, ReadTrackName
  }
}
