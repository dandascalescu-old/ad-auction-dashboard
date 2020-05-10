package com.comp2211.dashboard.io;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.model.data.Filter;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.util.Security;
import com.comp2211.dashboard.util.UserSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.ButtonGroup;

import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel;
import org.apache.commons.dbutils.DbUtils;

public class MockDatabaseManager extends DatabaseManager {

  private String TEST_USERNAME = "test";
  private String TEST_PASSWORD = "test";

  private Boolean open = true;

  public MockDatabaseManager() {
    this(
      "",
      "",
      "",
      "",
      ""
    );
  }

  public MockDatabaseManager(
      final String host,
      final String port,
      final String db,
      final String user,
      final String pw
  ) {
    super();

    click_table = Table.click_table.toString();
    impression_table = Table.impression_table.toString();
    server_table = Table.server_table.toString();

    open = true;
    verifyDatabaseTables();
  }

  public MockDatabaseManager(
      final String host,
      final String port,
      final String db,
      final String user,
      final String pw,
      final String c_table,
      final String i_table,
      final String s_table
  ) {
    this(host, port, db, user, pw);
  }

  @Override
  public void verifyDatabaseTables() {
    Logger.log("Verification complete.");
  }

  @Override
  public boolean isOpen() {
    return open;
  }

  /**
   * Retrieve the total for the specified type.
   * @param type the type of cost to retrieve.
   * @return BigDecimal representing the total cost.
   */
  @Override
  public BigDecimal retrieveTotalCost(Cost type, Filter filter) {
    return new BigDecimal("50.0");
  }

  /**
   * Retrieve the number of bounces by time
   * @param maxSeconds the maximum time in seconds for which a bounce is registered
   * @param allowInf whether entries with no exit time will be counted
   * @return long value of the number of bounces
   */
  @Override
  public long retrieveBouncesCountByTime(long maxSeconds, boolean allowInf, Filter filter) {
    return 50;
  }

  /**
   * Retrieve the number of bounces by number of pages visited
   * @param maxPages the maximum pages visited for which a bounce is registered
   * @return long value of the number of bounces
   */
  @Override
  public long retrieveBouncesCountByPages(byte maxPages, Filter filter) {
    return 50;
  }

  /**
   * Retrieve the total number of acquisitions
   * @return total count
   */
  @Override
  public long retrieveAcquisitionCount(Filter filter) {
    return 50;
  }

  /**
   * Retrieve the average acquisition cost.
   * @return the calculated average acquisition cost
   */
  @Override
  public BigDecimal retrieveAverageAcquisitionCost(Filter filter) {
    return new BigDecimal("0.05");
  }

  /**
   * Retrieve the average click cost for each date.
   * @return a map with each date as keys and the avg for that date as a value
   */
  @Override
  public HashMap<String, BigDecimal> retrieveDatedAverageCost(Cost type, Filter filter) {
    return this.getBigDecimalTestData();
  }

  /**
   * Retrieve the average acquisition cost for each date.
   * @return a map with each date as keys and the avg for that date as a value
   */
  @Override
  public HashMap<String, BigDecimal> retrieveDatedAverageAcquisitionCost(Filter filter) {
    return this.getBigDecimalTestData();
  }


  /**
   * Retrieve the total number of impressions for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedImpressionTotals(Filter filter, byte hoursGranularity) {
    return this.getLongTestData();
  }

  /**
   * Retrieve the total number of clicks for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedClickTotals(Filter filter) {
    return this.getLongTestData();
  }

  /**
   * Retrieve the total number of uniques for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedUniqueTotals(Filter filter) {
    return this.getLongTestData();
  }

  /**
   * Retrieve the total number of bounces (by time) for each date.
   * @param maxSeconds the maximum time in seconds for which a bounce is registered
   * @param allowInf whether entries with no exit time will be counted
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedBounceTotalsByTime(long maxSeconds, boolean allowInf, Filter filter) {
    return this.getLongTestData();
  }

  /**
   * Retrieve the total number of bounces (by pages visited) for each date.
   * @param maxPages the maximum pages visited for which a bounce is registered
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedBounceTotalsByPages(byte maxPages, Filter filter) {
    return this.getLongTestData();
  }

  /**
   * Retrieve the total number of acquisitions for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedAcquisitionTotals(Filter filter) {
    return this.getLongTestData();
  }

  /**
   * Retrieve demographics with count
   * @param type the type of demographics to retrieve
   * @return a map with each demographic as keys and the count for that demographic as a value
   */
  @Override
  public HashMap<String, Long> retrieveDemographics(Demographic type, Filter filter) {
    return this.getLongTestData();
  }

  /**
   * Retrieve the amount of entries in the specified database table
   * @param table the Table to check
   * @return the amount of entries found
   */
  @Override
  public long retrieveDataCount(Table table, boolean unique, Filter filter) {
    return 10;
  }

  @Override
  public String retrieveCampaignName(int campaignID) {
    return "Demo Campaign";
  }

  /**
   * Attempt to login using given credentials and create UserSession
   * @param username the username to use during login
   * @param password the password to use during login
   * @return true if login is successful, false otherwise
   */
  @Override
  public boolean attemptUserLogin(String username, String password) {
    return (username.equals(TEST_USERNAME) && password.equals(TEST_PASSWORD));
  }

  private HashMap<String, Long> getLongTestData() {
    String data = "2015-01-01 22049|2015-01-02 32773|2015-01-03 34919|2015-01-04 33111|2015-01-05 35758|2015-01-06 37379|2015-01-07 37958|2015-01-08 37311|2015-01-09 39031|2015-01-10 36562|2015-01-11 42014|2015-01-12 40945|2015-01-13 42159|2015-01-14 14135";
    String[] dataPoints = data.split("\\|");

    HashMap<String, Long> datedImpressionTotals = new HashMap<>();

    for (String point : dataPoints) {
      String[] pointVals = point.split(" ");
      datedImpressionTotals.put(pointVals[0], Long.parseLong(pointVals[1]));
    }

    return datedImpressionTotals;
  }

  private HashMap<String, BigDecimal> getBigDecimalTestData() {
    String data = "2015-01-01 22049|2015-01-02 32773|2015-01-03 34919|2015-01-04 33111|2015-01-05 35758|2015-01-06 37379|2015-01-07 37958|2015-01-08 37311|2015-01-09 39031|2015-01-10 36562|2015-01-11 42014|2015-01-12 40945|2015-01-13 42159|2015-01-14 14135";
    String[] dataPoints = data.split("\\|");

    HashMap<String, BigDecimal> datedImpressionTotals = new HashMap<>();

    for (String point : dataPoints) {
      String[] pointVals = point.split(" ");
      datedImpressionTotals.put(pointVals[0], new BigDecimal(pointVals[1]));
    }

    return datedImpressionTotals;
  }

}
