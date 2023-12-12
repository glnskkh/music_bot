/*
package org.musicbotcom.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.musicbotcom.storage.Track;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(JUnit4.class)
public class TestAddTrack extends TestCommands {

  boolean wasTrackAdditionSuccessful(String name) {
    return wasLastActionSuccessful() && getDefaultUser().getPlaylist("123")
        .get().hasTrack(new Track(name, ""));
  }

  @Override
  public void setUpBot() throws TelegramApiException {
    super.setUpBot();

    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");
  }

  @Test
  public void testAddTrack() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage("123");
    sendDefaultUserMessage("Dancing Queen");

    assertTrue(wasTrackAdditionSuccessful("Dancing Queen"));
  }

  @Test
  public void testAddTrackWrongPlaylist() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage("321");

    assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage("123");
    sendDefaultUserMessage("Dancing Queen");

    assertTrue(wasTrackAdditionSuccessful("Dancing Queen"));
  }

  @Test
  public void testAddTrackExists() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage("123");
    sendDefaultUserMessage("Money");

    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage("123");
    sendDefaultUserMessage("Money");

    assertFalse(wasLastActionSuccessful());
  }
}
 */