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

  public boolean hasPlaylist(String playlistName) {
    return Playlist.hasPlaylist(getChatId(), playlistName);
  }

  public Playlist getPlaylist(String playlistName) {
    return Playlist.getPlaylist(getChatId(), playlistName);
  }

  public List<Track> getPlaylistContent(Playlist playlist) {
    return Track.fromPlaylist(getChatId(), playlist.name());
  }
}
