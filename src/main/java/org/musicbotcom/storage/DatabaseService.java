package org.musicbotcom.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class DatabaseService {
  private Connection connection;
  public DatabaseService() {
    try{
      // Адрес бд postgres на localhost
      Class.forName("org.postgresql.Driver");
      String url = "jdbc:postgresql://localhost:5432/postgres";

      // Создание свойств для соединения с бд
      Properties authorization = new Properties();
      authorization.put("user", "postgres");
      authorization.put("password", "postgres");

      // Создание соединения с бд
      this.connection = DriverManager.getConnection(url, authorization);

    } catch (Exception e) {
      System.err.println("Error accessing database!");
    }
  }
  public void Insert(String db_name, ArrayList<String> args, ArrayList<String> values) {
    try {
      String query = "INSERT INTO %s (%s) VALUES (%s)".formatted(
          db_name,
          String.join(", ", args),
          String.join(", ", values));
      Statement statement = connection.createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (SQLException e) {
      System.err.println("SQLException in Insert");
    }
  }
  public String Get_db(String db_name) {
    try {
      // Создание оператора доступа к базе данных
      Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
          ResultSet.CONCUR_UPDATABLE);

      // Выполнение запроса к базе данных, получение набора данных
      ResultSet table = statement.executeQuery("SELECT * FROM %s".formatted(db_name));

      StringBuilder result = new StringBuilder();

      table.first(); // имена полей
      for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
        result.append("%s\t\t".formatted(table.getMetaData().getColumnName(j)));
      }
      result.append("\n");

      table.beforeFirst(); // сами поля
      while (table.next()) {
        for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
          result.append("%s\t\t".formatted(table.getString(j)));
        }
        result.append("\n");
      }

      statement.close();
      return result.toString();

    } catch (SQLException e) {
      return "SQLException in reading db";
    }

  }
  public void Update(String db_name, HashMap<String, String> args, HashMap<String, String> where) {
    try {
      // создаем изменяемые аргументы
      StringBuilder sb_args = new StringBuilder();
      Set<String> set_args = args.keySet();
      for(String key : set_args) {
        sb_args.append(key);
        sb_args.append(" = '");
        sb_args.append(args.get(key));
        sb_args.append("'");
        set_args.remove(key);
        if(!(set_args.isEmpty())) {
          sb_args.append(", ");
        }
      }

      StringBuilder sb_where = new StringBuilder();
      Set<String> set_where = args.keySet();
      for(String key : set_where) {
        sb_where.append(key);
        sb_where.append(" = '");
        sb_where.append(where.get(key));
        sb_where.append("'");
        set_where.remove(key);
        if(!(set_where.isEmpty())) {
          sb_where.append(", ");
        }
      }

      // создаем запрос
      String query = "UPDATE %s SET %s WHERE %s;".formatted(
          db_name,
          sb_args.toString(),
          sb_where.toString());

      Statement statement = connection.createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (SQLException e) {
      System.err.println("SQLException in Update");
    }
  }

  public void Delete_row(String db_name, HashMap<String, String> where) {
    try {
      StringBuilder sb_where = new StringBuilder();
      for(String key : where.keySet()) {
        sb_where.append(key);
        sb_where.append(" = '");
        sb_where.append(where.get(key));
        sb_where.append("'");
      }
      System.out.println(sb_where);

      String query = "DELETE FROM %s WHERE %s".formatted(
          db_name,
          sb_where
      );

      Statement statement = connection.createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (Exception e) {
      System.err.println("SQLException in Delete");
    }
  }
}
