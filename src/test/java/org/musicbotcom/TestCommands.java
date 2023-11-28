package org.musicbotcom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(MockitoJUnitRunner.class)
class TestCommands {

  private MusicBot bot;
  private String lastAnswer;

  void sendMessage(long chatId, String messageText) {
    Chat chat = new Chat(chatId, "private");

    Message message = new Message();
    message.setChat(chat);
    message.setText(messageText);

    Update update = new Update();
    update.setMessage(message);

    bot.onUpdateReceived(update);
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

  @Test
  void testAddPlaylist() {
    sendMessage(1, "/addPlaylist");
    sendMessage(1, "123");

    bot.getUser(1).showPlaylists();
  }
}