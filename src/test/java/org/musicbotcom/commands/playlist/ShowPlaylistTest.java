package org.musicbotcom.commands.playlist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.musicbotcom.commands.CommandTest;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(JUnit4.class)
public class ShowPlaylistTest extends CommandTest {

  private final String testPlaylistName = "123";
  private final String otherPlaylistName = "312";

  boolean wasListedToUser(String trackName) {
    return getLastAnswer().contains(trackName);
  }

  boolean showedEmptyPlaylist() {
    return getLastAnswer().contains("Нет треков");
  }

  @Override
  public void setUpBot() throws TelegramApiException {
    super.setUpBot();

    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(testPlaylistName);

    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage(testTrackNames[0]);
  }

  @Test
  public void testShowAddedTrack() {
    sendDefaultUserMessage("/showPlaylist");
    sendDefaultUserMessage(testPlaylistName);

    assertTrue(wasListedToUser(testTrackNames[0]));
  }

  @Test
  public void testNotShowUnaddedTrack() {
    sendDefaultUserMessage("/showPlaylist");
    sendDefaultUserMessage(testPlaylistName);

    assertTrue(wasListedToUser(testTrackNames[0]));
    assertFalse(wasListedToUser(testTrackNames[1]));
  }

  @Test
  public void testNonExistingPlaylist() {
    sendDefaultUserMessage("/showPlaylist");
    sendDefaultUserMessage(otherPlaylistName);

    assertFalse(wasLastActionSuccessful());
  }

  @Test
  public void testEmptyPlaylist() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(otherPlaylistName);

    assertTrue(wasLastActionSuccessful());

    sendDefaultUserMessage("/showPlaylist");
    sendDefaultUserMessage(otherPlaylistName);

    assertTrue(showedEmptyPlaylist());
  }
}
