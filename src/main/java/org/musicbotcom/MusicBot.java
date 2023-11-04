package org.musicbotcom;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MusicBot extends TelegramLongPollingBot {

  private final String botUsername = "qwer213_bot";
  private final String token;

  public MusicBot(ApiKeyProvider keyProvider) {
    super();

    token = keyProvider.getApiKey();
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage message = new SendMessage();

    message.setChatId(update.getMessage().getChatId());
    message.setText(update.getMessage().getText());

    try {
      this.execute(message);
    } catch (TelegramApiException e) {
      throw new RuntimeException("Cannot send message to chat : " + e);
    }
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }
}
