package org.musicbotcom.commands.playlist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.musicbotcom.commands.CommandTest;

@RunWith(JUnit4.class)
public class ShowPlaylistsTest extends CommandTest {

  private final String firstPlaylistName = "123";
  private final String secondPlaylistName = "321";

  boolean wasPlaylistShowed(String playlistName) {
    return getLastAnswer().contains(playlistName);
  }

  @Test
  public void testShowedAddedPlaylist() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(firstPlaylistName);

    sendDefaultUserMessage("/showPlaylists");
    assertTrue(wasPlaylistShowed(firstPlaylistName));
  }

  @Test
  public void testNotShowedUnaddedPlaylist() {
    sendDefaultUserMessage("/addPlaylist");
    sendDefaultUserMessage(firstPlaylistName);

    sendDefaultUserMessage("/showPlaylists");

    assertTrue(wasPlaylistShowed(firstPlaylistName));
    assertFalse(wasPlaylistShowed(secondPlaylistName));
  }
}
