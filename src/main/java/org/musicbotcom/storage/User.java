package org.musicbotcom.storage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.database.PlaylistDatabase;
import org.musicbotcom.storage.database.TracksDatabase;

public class User {

  private final long chatId;
  private Command nextCommand = new ProcessCommand();

  public User(long chatId) {
    this.chatId = chatId;
  }

  public long getChatId() {
    return chatId;
  }

  public void changeNextCommand(Command nextCommand) {
    this.nextCommand = nextCommand;
  }

  public String nextState(String message) {
    Command command = nextCommand.react(message, this);

    String commandMessage = nextCommand.getMessage();

    nextCommand = command;

    return commandMessage;
  }

  public Optional<Playlist> getPlaylist(String playlistName) {
    List<Playlist> playlists;

    try {
      playlists = PlaylistDatabase.getAllPlaylists(this);
    } catch (SQLException e) {
      System.err.printf("Cannot get all playlists for user %d", chatId);

      return Optional.empty();
    }

    return playlists.stream()
        .filter(playlist -> playlist.name().equals(playlistName)).findFirst();
  }

  public void addPlaylist(String playlistName) {
    try {
      PlaylistDatabase.addPlaylist(this, playlistName);
    } catch (SQLException e) {
      System.err.printf("Cannot add playlist named %s for user %d%n",
          playlistName, chatId);
    }
  }

  public String showPlaylists() {
    try {
      return PlaylistDatabase.listAllPlaylists(this);
    } catch (SQLException e) {
      System.err.printf("Cannot show playlists for user %d%n", chatId);
      return "";
    }
  }

  public void removePlaylist(Playlist playlist) {
    try {
      PlaylistDatabase.removePlaylist(playlist);
    } catch (SQLException e) {
      System.err.printf("Cannot remove playlist %d for user %d%n",
          playlist.id(), chatId);
    }
  }

  public String showPlaylist(Playlist playlist) {
    try {
      return TracksDatabase.getAllTracks(playlist).stream().map(Track::name)
          .collect(Collectors.joining("\n"));
    } catch (SQLException e) {
      System.err.printf("Cannot show playlist %d for user %d", playlist.id(),
          chatId);
      return "";
    }
  }

  public List<Track> getTracks(Playlist playlist) {
    try {
      return TracksDatabase.getAllTracks(playlist);
    } catch (SQLException e) {
      System.err.printf("Cannot get tracks from playlist %d for user %d",
          playlist.id(), chatId);
      return new ArrayList<>();
    }
  }

  public void addTrack(Playlist playlist, Track track) {
    try {
      PlaylistDatabase.addTrack(playlist, track);
    } catch (SQLException e) {
      System.err.printf("Cannot add track %s in playlist %d for user %d",
          track.name(), playlist.id(), chatId);
    }
  }
}
