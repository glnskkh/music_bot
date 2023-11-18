package org.musicbotcom.storage;

import java.util.ArrayList;
import java.util.Optional;
import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;

public class User {

  private final long chatId;
  public final ArrayList<Playlist> playlists = new ArrayList<>();
  public Command nextCommand = new ProcessCommand();

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
}
