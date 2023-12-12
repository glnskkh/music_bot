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

public class TracksDatabase {

  private static List<Track> collectTracks(ResultSet results)
      throws SQLException {
    List<Track> playlists = new ArrayList<>();

    for (results.beforeFirst(); results.next(); ) {
      long id = results.getLong(1);
      String name = results.getString(2);
      String path = results.getString(3);

      playlists.add(new Track(id, name, path));
    }

    return playlists;
  }

  public static List<Track> getAllTracks(Playlist playlist)
      throws SQLException {
    PreparedStatement statement = DatabaseService.prepareStatement("""
        SELECT
          track_id,
          name,
          path
        FROM
          tracks
        RIGHT JOIN inclusion ON inclusion.track_id = tracks.track_id
        WHERE
          playlist_id = ?;
        """);
    statement.setLong(1, playlist.id());

    ResultSet results = statement.executeQuery();
    List<Track> tracks = collectTracks(results);

    statement.close();

    return tracks;
  }

  public static String listAllTracks(User user) throws SQLException {
    PreparedStatement statement = DatabaseService.prepareStatement("""
        SELECT
          tracks.name,
          playlists.name
        FROM
          tracks
          RIGHT JOIN inclusion ON inclusion.track_id = tracks.track_id
          RIGHT JOIN playlists ON playlists.playlist_id = inclusion.playlist_id
        WHERE
          chat_id = ?
        ORDER BY
        	playlists.name ASC;
        """);
    statement.setLong(1, user.getChatId());

    ResultSet results = statement.executeQuery();

    StringBuilder stringBuilder = new StringBuilder();

    String lastPlaylist = null;

    for (results.beforeFirst(); results.next(); ) {
      String trackName = results.getString(1);
      String playlistName = results.getString(2);

      if (!playlistName.equals(lastPlaylist)) {
        stringBuilder.append(playlistName);
        stringBuilder.append(':');
        stringBuilder.append('\n');

        lastPlaylist = playlistName;
      }

      stringBuilder.append(' ');
      stringBuilder.append(trackName);
      stringBuilder.append('\n');
    }

    return stringBuilder.toString();
  }
}

