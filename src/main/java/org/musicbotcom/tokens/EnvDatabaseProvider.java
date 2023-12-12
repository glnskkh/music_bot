package org.musicbotcom.tokens;

public class EnvDatabaseProvider implements DatabaseProvider {

  private boolean isValidAddress(String address) {
    final String addressAndPortRegex = "(\\d{1,3}\\.){3}\\d{1,3}:\\d{1,}";

    return address.matches(addressAndPortRegex);
  }

  @Override
  public String getConnectionString() {
    final String address = System.getenv("DB_ADDRESS");

    if (address == null) {
      throw new RuntimeException(
          "Cannot get database address from environment");
    }

    assert isValidAddress(address);

    return String.format("jdbc:postgresql://%s/postgres", address);
  }

  @Override
  public String getUsername() {
    final String username = System.getenv("DB_USERNAME");

    if (username == null) {
      throw new RuntimeException(
          "Cannot get database username from environment");
    }

    return username;
  }

  @Override
  public String getPassword() {
    final String password = System.getenv("DB_PASSWORD");

    if (password == null) {
      throw new RuntimeException(
          "Cannot get database password from environment");
    }

    return password;
  }
}
