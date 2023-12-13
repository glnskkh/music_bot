package org.musicbotcom.commands;

import org.musicbotcom.commands.playlist.AddPlaylist;
import org.musicbotcom.commands.playlist.DeletePlaylist;
import org.musicbotcom.commands.playlist.ShowPlaylist;
import org.musicbotcom.commands.playlist.ShowPlaylists;
import org.musicbotcom.commands.service.HelpCommand;
import org.musicbotcom.commands.service.StartCommand;
import org.musicbotcom.commands.service.UnknownCommand;
import org.musicbotcom.commands.tracks.AddTrack;
import org.musicbotcom.storage.User;

public class ProcessCommand implements SingleStateCommand {

  @Override
  public CommandResult react(String userInput, User user) {
    Command command = switch (userInput) {
      case "/addPlaylist" -> new AddPlaylist();
      case "/deletePlaylist" -> new DeletePlaylist();
      case "/showPlaylist" -> new ShowPlaylist();
      case "/addTrack" -> new AddTrack();
      case "/showPlaylists" -> new ShowPlaylists();

      case "/start" -> new StartCommand();
      case "/help" -> new HelpCommand();
      default -> new UnknownCommand();
    };

    return CommandResult.invocation(command, userInput, user);
  }
}
