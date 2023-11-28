package org.musicbotcom.storage;

import java.util.ArrayList;
import java.util.Optional;
import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;

public class User {

  private final ArrayList<Playlist> playlists = new ArrayList<>();
  private final long chatId;
  private Command nextCommand = new ProcessCommand();

  public User(long chatId) {
    this.chatId = chatId;
  }

  public long getChatId() {
    return chatId;
  }

  public String showPlaylists() {
    StringBuilder data = new StringBuilder();

    for (var playlist : playlists) {
      data.append(playlist.show());
      data.append('\n');
    }

    return data.toString();
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
    return playlists.stream().filter(playlist -> playlist.getName().equals(playlistName))
        .findFirst();
  }

  public void addPlaylist(String playlistName) {
    playlists.add(new Playlist(playlistName));
  }

  public void removePlaylist(String playlistName) {
    var playlist = getPlaylist(playlistName);

    if (playlist.isEmpty()) {
      throw new RuntimeException(
          "User %s has not got playlist %s".formatted(String.valueOf(chatId), playlistName));
    }

    playlists.remove(playlist.get());
  }
}
