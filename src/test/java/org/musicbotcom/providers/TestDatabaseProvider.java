package org.musicbotcom.providers;

import java.util.Optional;
import org.musicbotcom.tokens.DatabaseProvider;

public class TestDatabaseProvider implements DatabaseProvider {

  @Override
  public String getConnectionString() {
    return "jdbc:sqlite::memory:";
  }

  @Override
  public Optional<String> getUsername() {
    return Optional.empty();
  }

  @Override
  public Optional<String> getPassword() {
    return Optional.empty();
  }
}
