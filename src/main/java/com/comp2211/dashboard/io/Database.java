package com.comp2211.dashboard.io;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.comp2211.dashboard.util.Logger;

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

  public void open() {
    try {
      final String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
      connection = DriverManager.getConnection(url, user, password);
    } catch (final SQLException e) {
      Logger.log("MYSQL exception during connection.");
      e.printStackTrace();
    }
  }

  /**
   * Returns the current Connection.
   *
   * @return Connection if exists, else null
   */
  public Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        open();
      }
    } catch (final SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  /**
   * Check database to see if a table exists.
   *
   * @param table Table name to check
   * @return true if table exists, else false
   */
  public boolean tableExists(final String table) {
    boolean exists = false;
    try {
      if (connection == null || connection.isClosed()) {
        open();
      }
      final DatabaseMetaData dmd = connection.getMetaData();
      final ResultSet rs = dmd.getTables(null, null, table, null);

      exists = rs.next();
      rs.close();
    } catch (final SQLException e) {
      e.printStackTrace();
    }
    return exists;
  }
}
