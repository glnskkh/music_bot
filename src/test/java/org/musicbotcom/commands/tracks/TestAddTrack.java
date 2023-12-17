package org.musicbotcom.commands.tracks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.musicbotcom.commands.CommandTest;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(JUnit4.class)
public class TestAddTrack extends CommandTest {

  private final String testPlaylistName = "123";
  private final String nonExsistingPlaylistName = "321";


  boolean wasTrackAdditionSuccessful(String trackName) {
    return wasLastActionSuccessful() && getDefaultUser().hasTrackInPlaylist(
        trackName, testPlaylistName);
  }

  @Override
  public void setUpBot() throws TelegramApiException {
    super.setUpBot();

    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(testPlaylistName);
  }

  @Test
  public void testAddTrack() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage("Dancing");

    assertTrue(wasTrackAdditionSuccessful("Dancing"));
  }

  @Test
  public void testAddTrackWrongPlaylist() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(nonExsistingPlaylistName);

    assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage("Dancing");

    assertTrue(wasTrackAdditionSuccessful("Dancing"));
  }

  @Test
  public void testAddTrackExists() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage("Forever");

    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage("Forever");

    assertFalse(wasLastActionSuccessful());
  }
}