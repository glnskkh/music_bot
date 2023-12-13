package org.musicbotcom.commands;

import org.junit.Before;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.musicbotcom.MusicBot;
import org.musicbotcom.storage.User;
import org.musicbotcom.tokens.DatabaseProvider;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


class TestCommands {


  private final long DEFAULT_USER_CHAT_ID = 1;
  protected String lastAnswer;
  private MusicBot bot;

  User getDefaultUser() {
    return bot.getUser(DEFAULT_USER_CHAT_ID);
  }

  void sendMessage(long chatId, String messageText) {
    Chat chat = new Chat(chatId, "private");

    Message message = new Message();
    message.setChat(chat);
    message.setText(messageText);

    Update update = new Update();
    update.setMessage(message);

    bot.onUpdateReceived(update);
  }

  void sendDefaultUserMessage(String message) {
    sendMessage(DEFAULT_USER_CHAT_ID, message);
  }

  boolean wasLastActionSuccessful() {
    // Пока считаем, что в неправильной строке где-то встретится слово другой
    return !lastAnswer.contains("друг");
  }

  @Before
  public void setUpBot() throws TelegramApiException {
    bot = Mockito.spy(new MusicBot(() -> ""));

    Mockito.doAnswer(invocationOnMock -> {
      lastAnswer = ((SendMessage) invocationOnMock.getArguments()[0]).getText();
      return lastAnswer;
    }).when(bot).execute(ArgumentMatchers.any(SendMessage.class));

    Mockito.doCallRealMethod().when(bot)
        .onUpdateReceived(ArgumentMatchers.any());
  }
}