package org.musicbotcom.commands.playlist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.musicbotcom.commands.CommandTest;

@RunWith(JUnit4.class)
public class AddPlaylistTest extends CommandTest {

  private final String testPlaylistName = "123";

  boolean wasCreatedPlaylist(String name) {
    return getDefaultUser().hasPlaylist(name);
  }

  @Test
  public void testAddPlaylist() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(testPlaylistName);

    assertTrue(wasCreatedPlaylist(testPlaylistName));
  }

  @Test
  public void testAddPlaylistExists() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(testPlaylistName);
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(testPlaylistName);

    assertFalse(wasLastActionSuccessful());

    final String otherPlaylistName = "321";
    sendDefaultUserMessage(otherPlaylistName);

    assertTrue(wasCreatedPlaylist(otherPlaylistName));
  }
}