package org.musicbotcom.storage;

import java.util.List;

public record Playlist(long id, String name) {

  public static boolean contains(List<Track> tracks, Track track) {
    return tracks.stream()
        .anyMatch(track1 -> track1.name().equals(track.name()));
  }
}
