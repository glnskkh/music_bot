package org.musicbotcom.providers;

import org.musicbotcom.tokens.ApiKeyProvider;

public class TestApiKeyProvider implements ApiKeyProvider {

  @Override
  public String getApiKey() throws RuntimeException {
    return "";
  }
}
