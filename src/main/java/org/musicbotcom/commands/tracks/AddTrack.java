package org.musicbotcom.commands.tracks;

import java.util.List;
import java.util.stream.Collectors;
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
    if (!user.hasPlaylist(playlistName)) {
      var message = "Плейлист %s не существует, выберете другое имя!".formatted(
          playlistName);

      return CommandResult.notChangeCommand(this, message);
    }

    this.playlist = user.getPlaylist(playlistName);

    var message = "Введи название трека";

    state = States.ReadTrackName;

    return CommandResult.notChangeCommand(this, message);
  }

  private CommandResult reactReadTrackName(String trackName, User user) {
    if (!Track.hasTrack(trackName)) {
      var message = "Трека %s нет в базе, введите другой".formatted(trackName);

      return CommandResult.notChangeCommand(this, message);
    }

    List<Track> tracks = user.getTracks(playlist.name());
    var trackNames = tracks.stream().map(Track::name)
        .collect(Collectors.toSet());

    if (trackNames.contains(trackName)) {
      var message = "Трек уже добавлен %s в плейлист %s, введите другой".formatted(
          trackName, playlist.name());

      return CommandResult.notChangeCommand(this, message);
    }

    user.addTrack(playlist.name(), trackName);

    var message = "Трек успешно добавлен %s в плейлист %s".formatted(trackName,
        playlist.name());

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
