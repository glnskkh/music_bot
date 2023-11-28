package org.musicbotcom.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class TestAddPlaylist extends TestCommands {

  boolean wasCreatedPlaylist(String name) {
    return wasLastActionSuccessful() && getDefaultUser().getPlaylist(name)
        .isPresent();
  }

  @Test
  void testAddPlaylist() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");

    assertTrue(wasCreatedPlaylist("123"));
  }

  @Test
  void testAddPlaylistExists() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");

    assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage("321");

    assertTrue(wasCreatedPlaylist("321"));
  }
}