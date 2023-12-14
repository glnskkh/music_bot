package org.musicbotcom.storage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.musicbotcom.DatabaseService;

public record Playlist(int id, String name) {

  public static boolean hasPlaylist(long chatId, String playlistName) {
    String queryPlaylistName = """
        select
        	count(*)
        from
        	playlists
        where
        	playlists.chat_id = ? and
        	playlists.name = ?;
        """;

    try (var statement = DatabaseService.prepareStatement(queryPlaylistName)) {
      statement.setLong(1, chatId);
      statement.setString(2, playlistName);

      var results = statement.executeQuery();

      int count = results.getInt(1);

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
      statement.setString(2, playlistName);

      var results = statement.executeQuery();

      int id = results.getInt("playlist_id");
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

      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot add playlist %s".formatted(playlistName));
    }
  }

  public static void deletePlaylist(int playlistId) {
    var queryDeletePlaylist = """
        delete from
          playlists
        where
          playlists.playlist_id = ?;
        """;

    var queryDeletePlaylistConnections = """
        delete from
          inclusion
        where
          inclusion.playlist_id = ?;
        """;

    try (var statementDeletePlaylist = DatabaseService.prepareStatement(
        queryDeletePlaylist); var statementDeletePlaylistConnections = DatabaseService.prepareStatement(
        queryDeletePlaylistConnections)) {
      statementDeletePlaylist.setInt(1, playlistId);
      statementDeletePlaylistConnections.setInt(1, playlistId);

      statementDeletePlaylist.execute();
      statementDeletePlaylistConnections.execute();
    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot delete playlist %d".formatted(playlistId));
    }
  }

  public static void addTrack(long chatId, String playlistName,
      String trackName) {
    Track track = Track.fromName(trackName);
    Playlist playlist = Playlist.getPlaylist(chatId, playlistName);

    var queryDeletePlaylist = """
        insert into
        	inclusion(playlist_id, track_id)
        values
        	(?, ?);
        """;

    try (var statement = DatabaseService.prepareStatement(
        queryDeletePlaylist)) {
      statement.setLong(1, playlist.id());
      statement.setLong(2, track.id());

      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot add track %d to playlist %d".formatted(track.id(),
              playlist.id()));
    }
  }

  public static List<Playlist> getPlaylists(long chatId) {
    var queryAllPlaylists = """
        select
          playlists.playlist_id,
          playlists.name
        from
          playlists
        where
          playlists.chat_id = ?;
        """;

    try (var statement = DatabaseService.prepareStatement(queryAllPlaylists)) {
      statement.setLong(1, chatId);

      var results = statement.executeQuery();

      List<Playlist> result = new ArrayList<>();

      while (results.next()) {
        int id = results.getInt("playlist_id");
        String name = results.getString("name");

        result.add(new Playlist(id, name));
      }

      return result;
    } catch (SQLException e) {
      throw new RuntimeException(
          "Cannot get info on playlists for user %d".formatted(chatId));
    }
  }

  public static boolean hasTrack(long chatId, String playlistName,
      String trackName) {
    List<Track> tracks = Track.fromPlaylist(chatId, playlistName);
    Set<String> trackNames = tracks.stream().map(Track::name)
        .collect(Collectors.toSet());

    return trackNames.contains(trackName);
  }
}
