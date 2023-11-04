package org.musicbotcom;

import org.musicbotcom.token.EnvApiKeyProvider;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

  public static void main(String[] args) {
    try {
      TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);

      api.registerBot(new MusicBot(new EnvApiKeyProvider()));
    } catch (TelegramApiException e) {
      throw new RuntimeException("Cannot register telegram bot : " + e);
    }
  }
}
