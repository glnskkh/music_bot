package org.musicbotcom.storage;

import java.sql.SQLException;
import org.musicbotcom.DatabaseService;

public record Playlist(long id, String name) {

  public static boolean hasPlaylist(long chatId, String playlistName) {
    String queryPlaylistName = """
        select
        	count(*)
        from
        	playlists
        where
        	playlists.chat_id = ? and
        	playlists.name like ?;
        """;

    try (var statement = DatabaseService.prepareStatement(queryPlaylistName)) {
      statement.setLong(1, chatId);
      statement.setString(2, "%" + playlistName + "%");

      var results = statement.executeQuery();
      results.beforeFirst();

      int count = results.getInt("count");

      return count > 0;

    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot check %s availability\n".formatted(playlistName));
    }
  }

  public static Playlist getPlaylist(long chatId, String playlistName) {
    var queryPlaylistInfo = """
        select
        	playlists.playlist_id,
          playlists.name
        from
        	playlists
        where
        	playlists.chat_id = ? and
        	playlists.name like ?;
        """;

    try (var statement = DatabaseService.prepareStatement(queryPlaylistInfo)) {
      statement.setLong(1, chatId);
      statement.setString(2, "%" + playlistName + "%");

      var results = statement.executeQuery();
      results.beforeFirst();

      long id = results.getLong("playlist_id");
      String name = results.getString("name");

      return new Playlist(id, name);

    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot get playlist %s\n".formatted(playlistName));
    }
  }

  public static void addPlaylist(long chatId, String playlistName) {
    var queryAddPlaylist = """
        insert into
        	playlists (chat_id, name)
        values
        	(?, ?);
        """;

    try (var statement = DatabaseService.prepareStatement(queryAddPlaylist)) {
      statement.setLong(1, chatId);
      statement.setString(2, playlistName);

      statement.executeQuery();
    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot add playlist %s".formatted(playlistName));
    }
  }

  public static void deletePlaylist(long playlistId) {
    var queryDeletePlaylist = """
        delete from
          inclusion
        where
          inclusion.playlist_id = ?;
                
        delete from
          playlists
        where
          playlists.playlist_id = ?;
        """;

    try (var statement = DatabaseService.prepareStatement(
        queryDeletePlaylist)) {
      statement.setLong(1, playlistId);
      statement.setLong(2, playlistId);

      statement.executeQuery();
    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot add playlist %d".formatted(playlistId));
    }

  }

  public static void addTrack(long chatId, String playlistName, String trackName) {}
}
