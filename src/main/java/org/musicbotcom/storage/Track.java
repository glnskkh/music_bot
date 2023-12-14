package org.musicbotcom.storage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.musicbotcom.DatabaseService;

public record Track(long id, String name, String path) {

  public static boolean hasTrack(String trackName) {
    final String queryTrackName = """
        select
        	count(*)
        from
        	tracks
        where
        	tracks.name like ?;
        """;

    try (var statement = DatabaseService.prepareStatement(queryTrackName)) {
      statement.setString(1, "%" + trackName + "%");

      var results = statement.executeQuery();
      results.next();

      int count = results.getInt(1);

      return count > 0;

    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot check %s availability".formatted(trackName));
    }
  }

  public static List<Track> fromPlaylist(long chatId, String playlistName) {
    var queryTracksByPlaylist = """
        select
          tracks.track_id,
          tracks.name,
          tracks.path
        from
          tracks
          right join inclusion on inclusion.track_id = tracks.track_id
          right join playlists on playlists.playlist_id = inclusion.playlist_id
        where
          playlists.chat_id = ?
          and playlists.name = ?;
        """;

    try (var statement = DatabaseService.prepareStatement(
        queryTracksByPlaylist)) {
      statement.setLong(1, chatId);
      statement.setString(2, playlistName);

      var results = statement.executeQuery();

      List<Track> result = new ArrayList<>();

      while (results.next()) {
        long id = results.getLong("track_id");
        String name = results.getString("name");
        String path = results.getString("path");

        if (path == null) {
          continue;
        }

        result.add(new Track(id, name, path));
      }

      return result;

    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot check %s availability".formatted(playlistName));
    }
  }

  public static Track fromName(String trackName) {
    final String queryTrackName = """
        select
        	tracks.track_id,
        	tracks.name,
        	tracks.path
        from
        	tracks
        where
        	tracks.name like ?;
        """;

    try (var statement = DatabaseService.prepareStatement(queryTrackName)) {
      statement.setString(1, "%" + trackName + "%");

      var results = statement.executeQuery();
      results.next();

      long id = results.getLong("track_id");
      String name = results.getString("name");
      String path = results.getString("path");

      return new Track(id, name, path);
    } catch (SQLException e) {
      throw new RuntimeException("Cannot get %s track".formatted(trackName));
    }
  }
}
