package org.musicbotcom.token;

public class EnvApiKeyProvider implements ApiKeyProvider {

  @Override
  public String getApiKey() throws RuntimeException {
    String token = System.getenv("TOKEN");

    if (token == null) {
      throw new RuntimeException("Cannot get token from environment");
    }

    return token;
  }
}
