package org.musicbotcom.tokens;

import java.util.Optional;

public interface DatabaseProvider {

  String getConnectionString();

  Optional<String> getUsername();

  Optional<String> getPassword();
}
