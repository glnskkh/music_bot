package org.musicbotcom.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestAddPlaylist extends TestCommands {

  boolean wasCreatedPlaylist(String name) {
    return wasLastActionSuccessful() && getDefaultUser().getPlaylist(name)
        .isPresent();
  }

  @Test
  public void testAddPlaylist() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");

    assertTrue(wasCreatedPlaylist("123"));
  }

  @Test
  public void testAddPlaylistExists() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage("123");

    assertFalse(wasLastActionSuccessful());

    sendDefaultUserMessage("321");

    assertTrue(wasCreatedPlaylist("321"));
  }
}