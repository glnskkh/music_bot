package org.musicbotcom.commands;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(JUnit4.class)
public class TestDeletePlaylist extends TestCommands {

  boolean wasSuccessfullyDeleted() {
    return wasLastActionSuccessful();
  }

  @Override
  public void setUpBot() throws TelegramApiException {
    super.setUpBot();

    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");
  }

  @Test
  public void testDeletePlaylist() {
    sendDefaultUserMessage("/deletePlaylist");
    sendDefaultUserMessage("123");

    Assert.assertTrue(wasSuccessfullyDeleted());
  }

  @Test
  public void testDeletePlaylistExists() {
    sendDefaultUserMessage("/deletePlaylist");
    sendDefaultUserMessage("321");

    Assert.assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage("123");

    Assert.assertTrue(wasSuccessfullyDeleted());
  }
}