package org.musicbotcom.commands.playlist;


import java.util.stream.Collectors;
import org.musicbotcom.commands.CommandResult;
import org.musicbotcom.commands.SingleStateCommand;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.User;

public class ShowPlaylists implements SingleStateCommand {

  @Override
  public CommandResult react(String userInput, User user) {
    var message = user.getPlaylists().stream().map(Playlist::name)
        .map(x -> x + "\n").collect(Collectors.joining());

    return CommandResult.returnToEmptyState(message);
  }
}
