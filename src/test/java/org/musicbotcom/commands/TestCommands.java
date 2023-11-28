package org.musicbotcom.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.musicbotcom.MusicBot;
import org.musicbotcom.storage.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(MockitoJUnitRunner.class)
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

  @BeforeEach
  void setUp() throws TelegramApiException {
    bot = spy(new MusicBot(() -> ""));

    doAnswer(invocationOnMock -> {
      lastAnswer = ((SendMessage) invocationOnMock.getArguments()[0]).getText();
      return lastAnswer;
    }).when(bot).execute(isA(SendMessage.class));

    doCallRealMethod().when(bot).onUpdateReceived(any());
  }
}