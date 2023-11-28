package org.musicbotcom.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.musicbotcom.storage.Track;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(MockitoJUnitRunner.class)
public class TestAddTrack extends TestCommands {

  boolean wasTrackAdditionSuccessful(String name) {
    return wasLastActionSuccessful() && getDefaultUser().getPlaylist("123")
        .get().hasTrack(new Track(name, ""));
  }

  @Override
  void setUp() throws TelegramApiException {
    super.setUp();

    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");
  }

  @Test
  void testAddTrack() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage("123");
    sendDefaultUserMessage("Dancing Queen");

    assertTrue(wasTrackAdditionSuccessful("Dancing Queen"));
  }

  @Test
  void testAddTrackWrongPlaylist() {
    sendDefaultUserMessage("/addTrack");
    sendDefaultUserMessage("321");

    assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage("123");
    sendDefaultUserMessage("Dancing Queen");

    assertTrue(wasTrackAdditionSuccessful("Dancing Queen"));
  }
}
