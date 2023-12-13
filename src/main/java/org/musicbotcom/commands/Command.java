package org.musicbotcom.commands;

import org.musicbotcom.storage.User;

public interface Command {

  CommandResult react(String message, User user);
}
