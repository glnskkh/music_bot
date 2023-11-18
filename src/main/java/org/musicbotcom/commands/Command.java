package org.musicbotcom.commands;

import org.musicbotcom.storage.User;

public interface Command {
  Command react(String message, User user);
  String getMessage();
}
