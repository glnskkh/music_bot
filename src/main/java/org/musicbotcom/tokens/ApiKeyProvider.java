package org.musicbotcom.tokens;

public interface ApiKeyProvider {

  String getApiKey() throws RuntimeException;
}
