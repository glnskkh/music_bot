package org.musicbotcom.token;

public interface ApiKeyProvider {

  String getApiKey() throws RuntimeException;
}
