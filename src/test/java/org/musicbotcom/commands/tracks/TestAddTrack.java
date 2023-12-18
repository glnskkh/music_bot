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
    sendDefaultUserMessage(testTrackNames[0]);

    assertTrue(wasTrackAdditionSuccessful(testTrackNames[0]));
  }

  @Test
  public void testAddTrackWrongPlaylist() {
    String nonExistingPlaylistName = "321";

    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(nonExistingPlaylistName);

    assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage(testTrackNames[0]);

    assertTrue(wasTrackAdditionSuccessful(testTrackNames[0]));
  }

  @Test
  public void testAddTrackExists() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage(testTrackNames[1]);

    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage(testTrackNames[1]);

    assertFalse(wasLastActionSuccessful());
  }

  @Test
  public void testAddNonExistingTrack() {
    final String nonExistingTrack = "Fugue";

    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage(nonExistingTrack);

    assertFalse(wasLastActionSuccessful());
  }
}