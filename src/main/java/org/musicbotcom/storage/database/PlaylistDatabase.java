package org.musicbotcom.storage.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.musicbotcom.DatabaseService;
import org.musicbotcom.storage.Playlist;
import org.musicbotcom.storage.Track;
import org.musicbotcom.storage.User;

public class PlaylistDatabase {

  private static List<Playlist> collectPlaylists(ResultSet results)
      throws SQLException {
    List<Playlist> playlists = new ArrayList<>();

    for (results.beforeFirst(); results.next(); ) {
      long id = results.getLong(1);
      String name = results.getString(2);

      playlists.add(new Playlist(id, name));
    }

    return playlists;
  }

  public static List<Playlist> getAllPlaylists(User user) throws SQLException {
    PreparedStatement statement = DatabaseService.prepareStatement("""
        SELECT
          playlist_id,
          name
        FROM
          playlists
        WHERE
          chat_id = ?;
        """);
    statement.setLong(1, user.getChatId());

    ResultSet results = statement.executeQuery();
    List<Playlist> playlists = collectPlaylists(results);

    statement.close();

    return playlists;
  }

  public static void removePlaylist(Playlist playlist) throws SQLException {
    PreparedStatement statement = DatabaseService.prepareStatement("""
        DELETE FROM
          inclusion
        WHERE
          inclusion.playlist_id = ?;
                
        DELETE FROM
          playlists
        WHERE
          playlists.playlist_id = ?;
        """);
    statement.setLong(1, playlist.id());
    statement.setLong(2, playlist.id());

    statement.executeQuery();

    statement.close();
  }

  public static void addPlaylist(User user, String playlistName) throws SQLException {
    PreparedStatement statement = DatabaseService.prepareStatement("""
        INSERT INTO
          playlists (chat_id, name)
        VALUES
          (?, ?);
        """);
    statement.setLong(1, user.getChatId());
    statement.setString(2, playlistName);

    statement.executeQuery();

    statement.close();
  }

  public static String listAllPlaylists(User user) throws SQLException {
    PreparedStatement statement = DatabaseService.prepareStatement("""
        SELECT
          playlists.name
        FROM
          playlists
        WHERE
          playlists.chat_id = ?;
        """);
    statement.setLong(1, user.getChatId());

    ResultSet results = statement.executeQuery();

    StringBuilder stringBuilder = new StringBuilder();

    for (results.beforeFirst(); results.next(); ) {
      String playlistName = results.getString(1);

      stringBuilder.append(playlistName);
      stringBuilder.append('\n');
    }

    return stringBuilder.toString();
  }

  public static void addTrack(Playlist playlist, Track track) throws SQLException {
    PreparedStatement statement = DatabaseService.prepareStatement("""
        INSERT INTO
          inclusion (playlist_id, track_id)
        VALUES
          (?, ?);
        """);
    statement.setLong(1, playlist.id());
    statement.setLong(2, track.id());

    statement.executeQuery();

    statement.close();
  }
}
