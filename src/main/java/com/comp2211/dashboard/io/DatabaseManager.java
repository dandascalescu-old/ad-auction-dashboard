package com.comp2211.dashboard.io;

import com.comp2211.dashboard.model.data.Filter;
import java.math.BigDecimal;
import java.util.HashMap;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel;

public abstract class DatabaseManager {

  protected Database sqlDatabase;
  protected boolean open = false;
  protected String click_table, impression_table, server_table, campaign_table;

  public enum Table { click_table, impression_table, server_table, campaign_table }
  public enum Cost { Click_Cost, Impression_Cost }

  /**
   * Initialise the database connection using info from the configuration file
   */
  public DatabaseManager() {}

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

  public String getCampaign_table() {
    return campaign_table;
  }

  /**
   * Retrieve the total cost (Click_Cost or Impression_Cost).
   */
  public abstract BigDecimal retrieveTotalCost(Cost type, Filter filter);

  /**
   * Retrieve the number of bounces by time
   */
  public abstract long retrieveBouncesCountByTime(long maxSeconds, boolean allowInf, Filter filter);

  /**
   * Retrieve the number of bounces by pages visited
   */
  public abstract long retrieveBouncesCountByPages(byte maxPages, Filter filter);

  /**
   * Retrieve the total number of acquisitions
   */
  public abstract long retrieveAcquisitionCount(Filter filter);

  /**
   * Retrieve the average acquisition cost.
   */
  public abstract BigDecimal retrieveAverageAcquisitionCost(Filter filter);

  /**
   * Retrieve the average cost for each date for the specified type.
   */
  public abstract HashMap<String, BigDecimal> retrieveDatedAverageCost(Cost type, Filter filter);

  /**
   * Retrieve the average acquisition cost for each date.
   */
  public abstract HashMap<String, BigDecimal> retrieveDatedAverageAcquisitionCost(Filter filter);

  /**
   * Retrieve the total number of impressions for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedImpressionTotals(byte hoursGranularity, Filter filter);

  /**
   * Retrieve the total number of clicks for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedClickTotals(byte hoursGranularity, Filter filter);

  /**
   * Retrieve the total number of uniques for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedUniqueTotals(byte hoursGranularity, Filter filter);

  /**
   * Retrieve the total number of bounces (by time) for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedBounceTotalsByTime(byte hoursGranularity, long maxSeconds, boolean allowInf, Filter filter);

  /**
   * Retrieve the total number of bounces (by pages visited) for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedBounceTotalsByPages(byte hoursGranularity, byte maxPages, Filter filter);

  /**
   * Retrieve the total number of acquisitions for each date.
   */
  public abstract HashMap<String, Long> retrieveDatedAcquisitionTotals(byte hoursGranularity, Filter filter);

  /**
   * Retrieve demographic info
   */
  public abstract HashMap<String, Long> retrieveDemographics(Demographic type, Filter filter);

  /**
   * Retrieve the amount of entries in the specified database table
   */
  public abstract long retrieveDataCount(Table table, boolean unique, Filter filter);

  public abstract String retrieveCampaignName(int campaignID);

  public long retrieveDataCount(Table table, Filter filter) {
    return retrieveDataCount(table, false, filter);
  }

  /**
   * Attempt to login using given credentials and create UserSession
   */
  public abstract boolean attemptUserLogin(String username, String password);
}