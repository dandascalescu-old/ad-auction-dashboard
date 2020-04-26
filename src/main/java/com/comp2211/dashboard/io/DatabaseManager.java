package com.comp2211.dashboard.io;

import java.math.BigDecimal;
import java.util.HashMap;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.util.Logger;

public abstract class DatabaseManager {

  protected Database sqlDatabase;
  protected boolean open = false;
  protected String click_table, impression_table, server_table;

  public static enum Table { click_table, impression_table, server_table };
  public static enum Cost { Click_Cost, Impression_Cost };

  /**
   * Initialise the database connection using info from the configuration file
   */
  public DatabaseManager(final String host, final String port, final String db, final String user, final String pw, final String c_table, final String i_table, final String s_table) {
    sqlDatabase = new Database(host, port, db, user, pw);

    click_table = c_table;
    impression_table = i_table;
    server_table = s_table;

    if (sqlDatabase.getConnection() == null) {
      Logger.log("Cannot establish database connection. Exiting now.");
      return;
    }
    Logger.log("Database connection established.");
    open = true;
    verifyDatabaseTables();
  }

  /**
   * Verifies and prints if any database tables aren't available
   */
  public abstract void verifyDatabaseTables();

  /**
   * @return whether the database connection was opened successfully
   */
  public abstract boolean isOpen();

  public String getClickTable() {
    return click_table;
  }

  public String getImpressionTable() {
    return impression_table;
  }

  public String getServerTable() {
    return server_table;
  }

  /**
   * Retrieve the total cost (Click_Cost or Impression_Cost).
   */
  public abstract BigDecimal retrieveTotalCost(Cost type);

  /**
   * Retrieve the number of bounces by time
   */
  public abstract long retrieveBouncesCountByTime(long maxSeconds, boolean allowInf);

  /**
   * Retrieve the number of bounces by pages visited
   */
  public abstract long retrieveBouncesCountByPages(byte maxPages);

  /**
   * Retrieve the total number of acquisitions
   */
  public abstract long retrieveAcquisitionCount();

  /**
   * Retrieve the average acquisition cost.
   */
  public abstract BigDecimal retrieveAverageAcquisitionCost();

  /**
   * Retrieve the average cost for each date for the specified type.
   */
  public abstract HashMap<String, BigDecimal> retrieveDatedAverageCost(Cost type);

  /**
   * Retrieve the average acquisition cost for each date.
   */
  public abstract HashMap<String, BigDecimal> retrieveDatedAverageAcquisitionCost();

  /**
   * Retrieve the total number of impressions for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedImpressionTotals();

  /**
   * Retrieve the total number of clicks for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedClickTotals();

  /**
   * Retrieve the total number of uniques for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedUniqueTotals();

  /**
   * Retrieve the total number of bounces (by time) for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedBounceTotalsByTime(long maxSeconds, boolean allowInf);

  /**
   * Retrieve the total number of bounces (by pages visited) for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedBounceTotalsByPages(byte maxPages);

  /**
   * Retrieve the total number of acquisitions for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedAcquisitionTotals();

  /**
   * Retrieve demographic info
   */
  public abstract HashMap<String, Long> retrieveDemographics(Demographic type);

  /**
   * Retrieve the amount of entries in the specified database table
   */
  public abstract long retrieveDataCount(String table, boolean unique);

  public long retrieveDataCount(String table) {
    return retrieveDataCount(table, false);
  }

  /**
   * Attempt to login using given credentials and create UserSession
   */
  public abstract boolean attemptUserLogin(String username, String password);
}