package org.musicbotcom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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