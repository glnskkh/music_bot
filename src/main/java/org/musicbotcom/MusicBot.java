package org.musicbotcom;

import java.util.HashMap;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.User;
import org.musicbotcom.token.ApiKeyProvider;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MusicBot extends TelegramLongPollingBot {

  private final String token;
  private final HashMap<Long, User> userHashMap = new HashMap<>();

  public MusicBot(ApiKeyProvider keyProvider) throws ClassNotFoundException {
    super();

    token = keyProvider.getApiKey();
  }

  @Override
  public String getBotToken() {
    return token;
  }

  public User getUser(long chatId) {
    if (!userHashMap.containsKey(chatId)) {
      userHashMap.put(chatId, new User(chatId));

    }
    return userHashMap.get(chatId);
  }

  private SendMessage processCommand(User user, Update update) {
    boolean isCommand = update.getMessage().getText().startsWith("/");

    if (isCommand) {
      user.changeNextCommand(new ProcessCommand());
    }

    String response = user.nextState(update.getMessage().getText());

    SendMessage message = new SendMessage();
    message.setChatId(update.getMessage().getChatId());
    message.setText(response);

    return message;
  }

  @Override
  public void onUpdateReceived(Update update) {
    long chatId = update.getMessage().getChatId();
    User user = getUser(chatId);

    SendMessage message = processCommand(user, update);

    try {
      this.execute(message);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getBotUsername() {
    return "qwer213_bot";
  }
}
