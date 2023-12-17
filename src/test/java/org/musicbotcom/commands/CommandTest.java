package org.musicbotcom.commands;

import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.musicbotcom.DatabaseService;
import org.musicbotcom.MusicBot;
import org.musicbotcom.providers.TestApiKeyProvider;
import org.musicbotcom.providers.TestDatabaseProvider;
import org.musicbotcom.storage.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CommandTest {

  protected static String[] testTrackNames = {"Dancing", "Forever"};
  private final long DEFAULT_USER_CHAT_ID = 1;
  private String lastAnswer;
  private MusicBot bot;

  private static void createTestTables() throws SQLException {
    final String createUserTable = """
        CREATE TABLE IF NOT EXISTS
          users (chat_id BIGINT PRIMARY KEY);
        """;
    final String createPlaylistsTable = """
        CREATE TABLE IF NOT EXISTS
          playlists (
            playlist_id  INTEGER PRIMARY KEY AUTOINCREMENT,
            chat_id BIGINT NOT NULL,
            name VARCHAR(128) NOT NULL
          );
        """;
    final String createTracksTable = """
        CREATE TABLE IF NOT EXISTS
          tracks (
            track_id INTEGER PRIMARY KEY AUTOINCREMENT,
            name VARCHAR(128) NOT NULL,
            path VARCHAR(128) NOT NULL
          );
        """;
    final String createInclusionTable = """
        CREATE TABLE IF NOT EXISTS
          inclusion (
            playlist_id BIGINT NOT NULL,
            track_id BIGINT NOT NULL
          );
        """;

    final String[] createQueries = {createUserTable, createPlaylistsTable,
        createTracksTable, createInclusionTable};

    for (var query : createQueries) {
      var statement = DatabaseService.prepareStatement(query);
      statement.execute();
      statement.close();
    }
  }

  private static void initDatabaseService() {
    DatabaseService.start(new TestDatabaseProvider());

    try {
      createTestTables();
      fillTestTables();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static void fillTestTables() throws SQLException {
    final String basicTracks = """
        INSERT INTO
          tracks (name, path)
        VALUES
          (?, '/dev/null'),
          (?, '/dev/null');
        """;

    var statement = DatabaseService.prepareStatement(basicTracks);
    statement.setString(1, testTrackNames[0]);
    statement.setString(2, testTrackNames[1]);

    statement.execute();
    statement.close();
  }

  @BeforeClass
  public static void setUpServices() {
    initDatabaseService();
  }

  protected String getLastAnswer() {
    return lastAnswer;
  }

  protected User getDefaultUser() {
    return bot.getUser(DEFAULT_USER_CHAT_ID);
  }

  protected void sendMessage(long chatId, String messageText) {
    Chat chat = new Chat(chatId, "private");

    Message message = new Message();
    message.setChat(chat);
    message.setText(messageText);

    Update update = new Update();
    update.setMessage(message);

    bot.onUpdateReceived(update);
  }

  protected void sendDefaultUserMessage(String message) {
    sendMessage(DEFAULT_USER_CHAT_ID, message);
  }

  public boolean wasLastActionSuccessful() {
    // Пока считаем, что в неправильной строке где-то встретится слово другой
    return !lastAnswer.contains("друг");
  }

  private void initBotMocks() throws TelegramApiException {
    Mockito.doAnswer(invocationOnMock -> {
      lastAnswer = ((SendMessage) invocationOnMock.getArguments()[0]).getText();
      return lastAnswer;
    }).when(bot).execute(ArgumentMatchers.any(SendMessage.class));

    Mockito.doCallRealMethod().when(bot)
        .onUpdateReceived(ArgumentMatchers.any());
  }

  @Before
  public void setUpBot() throws TelegramApiException {
    bot = Mockito.spy(new MusicBot(new TestApiKeyProvider()));

    initBotMocks();
  }

  @After
  public void clearDataBase() throws SQLException {
    var statementDeleteFromPlaylist = DatabaseService.prepareStatement(
        "delete from playlists;");
    var statementDeleteFromInclusion = DatabaseService.prepareStatement(
        "delete from inclusion;");

    statementDeleteFromPlaylist.execute();
    statementDeleteFromInclusion.execute();

    statementDeleteFromPlaylist.close();
    statementDeleteFromInclusion.close();
  }
}