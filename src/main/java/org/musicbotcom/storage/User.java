package org.musicbotcom.storage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.CommandResult;
import org.musicbotcom.commands.ProcessCommand;

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
    CommandResult commandResult = nextCommand.react(message, this);

    nextCommand = commandResult.nextCommand();

    return commandResult.message();
  }

  public List<Playlist> getAllPlaylists(String playlistName) {
    try {
      return Playlist.getAllPlaylists(this);
    } catch (SQLException e) {
      System.err.printf("Cannot get all playlists for user %d", chatId);

      return new ArrayList<>();
    }
  }

  public void addPlaylist(String playlistName) {
    try {
      Playlist.addPlaylist(this, playlistName);
    } catch (SQLException e) {
      System.err.printf("Cannot add playlist named %s for user %d%n",
          playlistName, chatId);
    }
  }

  public Optional<Playlist> getPlaylist(String playlistName) {
    try {
      return Playlist.getPlaylist(this, playlistName);
    } catch (SQLException e) {
      System.err.printf("Cannot get playlist %s for user %d", playlistName,
          chatId);
      return Optional.empty();
    }
  }

  public String showPlaylists() {
    try {
      return Playlist.listAllPlaylists(this);
    } catch (SQLException e) {
      System.err.printf("Cannot show playlists for user %d%n", chatId);
      return "";
    }
  }

  public void removePlaylist(Playlist playlist) {
    try {
      Playlist.removePlaylist(playlist);
    } catch (SQLException e) {
      System.err.printf("Cannot remove playlist %d for user %d%n",
          playlist.id(), chatId);
    }
  }

  public String showPlaylist(Playlist playlist) {
    try {
      return Track.getAllTracks(playlist).stream().map(Track::name)
          .collect(Collectors.joining("\n"));
    } catch (SQLException e) {
      System.err.printf("Cannot show playlist %d for user %d", playlist.id(),
          chatId);
      return "";
    }
  }

  public List<Track> getTracks(Playlist playlist) {
    try {
      return Track.getAllTracks(playlist);
    } catch (SQLException e) {
      System.err.printf("Cannot get tracks from playlist %d for user %d",
          playlist.id(), chatId);
      return new ArrayList<>();
    }
  }

  public void addTrack(Playlist playlist, Track track) {
    try {
      Playlist.addTrack(playlist, track);
    } catch (SQLException e) {
      System.err.printf("Cannot add track %s in playlist %d for user %d",
          track.name(), playlist.id(), chatId);
    }
  }
}
