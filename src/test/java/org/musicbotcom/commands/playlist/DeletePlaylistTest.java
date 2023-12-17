package org.musicbotcom.commands.playlist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.musicbotcom.commands.CommandTest;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(JUnit4.class)
public class DeletePlaylistTest extends CommandTest {

  private final String testPlaylistName = "123";

  boolean wasSuccessfullyDeleted() {
    return !getDefaultUser().hasPlaylist(testPlaylistName);
  }

  @Override
  public void setUpBot() throws TelegramApiException {
    super.setUpBot();

    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(testPlaylistName);
  }

  @Test
  public void testDeletePlaylist() {
    sendDefaultUserMessage("/deletePlaylist");
    sendDefaultUserMessage(testPlaylistName);

    assertTrue(wasSuccessfullyDeleted());
  }

  @Test
  public void testDeletePlaylistExists() {
    sendDefaultUserMessage("/deletePlaylist");

    final String otherPlaylistName = "321";
    sendDefaultUserMessage(otherPlaylistName);

    assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage(testPlaylistName);

    assertTrue(wasSuccessfullyDeleted());
  }
}