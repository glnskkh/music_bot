package org.musicbotcom.storage;

public class Track {
  private String name;
  private String path;

  public Track(String name, String path) {
    this.name = name;
    this.path = path;
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
