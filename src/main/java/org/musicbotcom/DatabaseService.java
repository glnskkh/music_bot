package org.musicbotcom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.musicbotcom.tokens.DatabaseProvider;

public class DatabaseService {

  private static Connection connection;

  public static void start(DatabaseProvider credentials) {
    String url = credentials.getConnectionString();

    if (credentials.getUsername() != null
        && credentials.getPassword() != null) {
      Properties authorization = new Properties();
      authorization.put("user", credentials.getUsername());
      authorization.put("password", credentials.getPassword());

      try {
        connection = DriverManager.getConnection(url, authorization);
      } catch (Exception e) {
        throw new RuntimeException("Cannot connect to database");
      }
    } else {
      try {
        connection = DriverManager.getConnection(url);
      } catch (Exception e) {
        throw new RuntimeException("Cannot connect to database");
      }
    }

  }

  public static PreparedStatement prepareStatement(String query)
      throws SQLException {
    return connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY);
  }

  public static void stop() {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException("Cannot close database connection");
    }
  }
}
