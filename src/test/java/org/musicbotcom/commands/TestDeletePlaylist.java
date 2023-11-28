package org.musicbotcom.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(MockitoJUnitRunner.class)
class TestDeletePlaylist extends TestCommands {

  boolean wasSuccessfullyDeleted(String name) {
    return wasLastActionSuccessful() && getDefaultUser().getPlaylist(name)
        .isEmpty();
  }

  @Override
  void setUp() throws TelegramApiException {
    super.setUp();

    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");
  }

  @Test
  void testDeletePlaylist() {
    sendDefaultUserMessage("/deletePlaylist");
    sendDefaultUserMessage("123");

    assertTrue(wasSuccessfullyDeleted("123"));
  }

  @Test
  void testDeletePlaylistExists() {
    sendDefaultUserMessage("/deletePlaylist");
    sendDefaultUserMessage("321");

    assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage("123");

    assertTrue(wasSuccessfullyDeleted("123"));
  }
}
