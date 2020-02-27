package com.comp2211.dashboard.io;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database {

  protected Connection connection = null;
  protected String host, port, database, user, password;

  public Database(final String passedHost, final String passedPort, final String passedDatabase, final String passedUser, final String passedPassword) {
    host = passedHost;
    port = passedPort;
    database = passedDatabase;
    user = passedUser;
    password = passedPassword;
  }

  public Connection open() {
    try {
      //Class.forName("com.mysql.cj.jdbc.Driver");
      final String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
      connection = DriverManager.getConnection(url, user, password);
      return connection;
    } catch (SQLException e) {
      System.out.println("MYSQL exception during connection.");
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Returns the current Connection.
   *
   * @return Connection if exists, else null
   */
  public Connection getConnection() {
    return connection;
  }

  /**
   * Close connection to database.
   */
  public void close() {
    if (connection != null) {
      try {
        connection.close();
      }
      catch (final SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Queries the database, for queries which return results.
   *
   * @param query Query to run
   * @return Result set of ran query
   */
  public ResultSet readQuery(final String query) {
    try {
      if (connection == null || connection.isClosed()) {
        open();
      }
      final PreparedStatement stmt = connection.prepareStatement(query);
      final ResultSet rs = stmt.executeQuery();

      return rs;
    }
    catch (final SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Check database to see if a table exists.
   *
   * @param table Table name to check
   * @return true if table exists, else false
   */
  public boolean tableExists(final String table) {
    try {
      if (connection == null || connection.isClosed()) {
        open();
      }
      final DatabaseMetaData dmd = connection.getMetaData();
      final ResultSet rs = dmd.getTables(null, null, table, null);

      return rs.next();
    }
    catch (final Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public synchronized void doQuery(final String query) {
    try {
      if (connection == null || connection.isClosed()) {
        open();
      }
      final PreparedStatement stmt = connection.prepareStatement(query);
      stmt.execute();
      stmt.close();
    }
    catch (final SQLException e) {
      e.printStackTrace();
    }
  }

}
