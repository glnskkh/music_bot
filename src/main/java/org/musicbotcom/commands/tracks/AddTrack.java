package org.musicbotcom.commands.tracks;

import org.musicbotcom.commands.Command;
import org.musicbotcom.commands.ProcessCommand;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.Track;
import org.musicbotcom.storage.User;

public class AddTrack implements Command {

  private Type type;
  private String message;
  private Playlist playlist;

  public AddTrack() {
    this.type = Type.EnterPlaylist;
  }

  @Override
  public Command react(String message, User user) {
    return switch (type) {
      case EnterPlaylist -> {
        var playlist = user.getPlaylist(message);

        if (playlist.isPresent()) {
          this.message = "Введи название трека";

          this.playlist = playlist.get();

          type = Type.EnterTrack;

          yield this;
        } else {
          this.message = "Плейлист %s не существует, выберете другое имя!".formatted(
              message);

          yield new AddTrack();
        }
      }
      case EnterTrack -> {
        Track example = new Track(message, "");

        if (playlist.hasTrack(example)) {
          this.message = "Трек уже добавлен %s в плейлист %s, введите другой".formatted(
              example.show(), playlist.getName());

          yield new ProcessCommand();
        }

        playlist.addTrack(example);

        this.message = "Трек успешный добавлен %s в плейлист %s".formatted(
            example.show(), playlist.getName());

        yield this;
      }
    };
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public enum Type {
    EnterPlaylist, EnterTrack
  }
}
