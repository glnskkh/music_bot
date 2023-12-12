package org.musicbotcom;

import org.musicbotcom.tokens.ApiKeyProvider;
import org.musicbotcom.tokens.DatabaseProvider;
import org.musicbotcom.tokens.EnvApiKeyProvider;
import org.musicbotcom.tokens.EnvDatabaseProvider;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

  public static void main(String[] args) throws TelegramApiException {
    TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);

    ApiKeyProvider keyProvider = new EnvApiKeyProvider();
    DatabaseProvider databaseProvider = new EnvDatabaseProvider();

    DatabaseService.start(databaseProvider);
    api.registerBot(new MusicBot(keyProvider));
  }
}
