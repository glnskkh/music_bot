package org.musicbotcom.storage;

import java.util.ArrayList;

public class Playlist {

  private final String name;
  private final ArrayList<Track> tracks = new ArrayList<>();

  public Playlist(String name) {
    this.name = name;
  }

  public void addTrack(Track track) {
    tracks.add(track);
  }

  public boolean hasTrack(Track track) {
    return tracks.contains(track);
  }

  public void deleteTrack(Track track) {
    if (!hasTrack(track)) {
      throw new RuntimeException(
          "Try to remove nonexistent track %s".formatted(track.toString()));
    }

    tracks.remove(track);
  }

  public String show() {
    StringBuilder playlist = new StringBuilder();

    playlist.append(name);
    playlist.append(":\n");

    for (var track : tracks) {
      playlist.append(track.show());
      playlist.append('\n');
    }

    return playlist.toString();
  }

  public String getName() {
    return name;
  }
}
