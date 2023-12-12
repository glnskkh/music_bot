package org.musicbotcom.tokens;

public interface DatabaseProvider {
  String getConnectionString();
  String getUsername();
  String getPassword();
}
