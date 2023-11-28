package org.musicbotcom.storage;

public class Track {

  private final String name;
  private final String path;

  public Track(String name, String path) {
    this.name = name;
    this.path = path;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Track)) {
      return false;
    }

    return name.equals(((Track) obj).name) &&
        path.equals(((Track) obj).path);
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public String show() {
    return this.name;
  }

  @Override
  public String toString() {
    return "%s %s".formatted(this.name, this.path);
  }
}
