package org.musicbotcom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.Assertions;
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
class TestAddPlaylist extends TestCommands {
  @Test
  void testAddPlaylist() {
    sendMessage(1, "/addPlaylist");
    sendMessage(1, "123");

    assertTrue(wasLastActionSuccessful());
  }

  @Test
  void testAddPlaylistExists() {
    sendMessage(1, "/addPlaylist");
    sendMessage(1, "123");
    sendMessage(1, "/addPlaylist");
    sendMessage(1, "123");

    assertFalse(wasLastActionSuccessful());

    sendMessage(1, "321");

    assertTrue(wasLastActionSuccessful());
  }
}