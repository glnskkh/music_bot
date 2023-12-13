package org.musicbotcom.storage;

import java.util.List;
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

  public boolean hasPlaylist(String playlistName) {
    return Playlist.hasPlaylist(getChatId(), playlistName);
  }

  public Playlist getPlaylist(String playlistName) {
    return Playlist.getPlaylist(getChatId(), playlistName);
  }

  public void deletePlaylist(String playlistName) {
    Playlist playlist = getPlaylist(playlistName);

    Playlist.deletePlaylist(playlist.id());
  }

  public void addPlaylist(String playlistName) {
    Playlist.addPlaylist(getChatId(), playlistName);
  }

  public String showPlaylist(String playlistName) {
    return getTracks(playlistName).stream().map(Track::name).map(x -> x + "\n")
        .collect(Collectors.joining());
  }

  public List<Track> getTracks(String playlistName) {
    return Track.fromPlaylist(getChatId(), playlistName);
  }

  public void addTrack(String playlistName, String trackName) {
    Playlist.addTrack(getChatId(), playlistName, trackName);
  }

  public List<Playlist> getPlaylists() {
    return Playlist.getPlaylists(getChatId());
  }
}
