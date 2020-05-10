package com.comp2211.dashboard.io;

import com.comp2211.dashboard.model.data.Filter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.comp2211.dashboard.util.Logger;
import org.apache.commons.dbutils.DbUtils;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.util.Security;
import com.comp2211.dashboard.util.UserSession;

public class MySQLManager extends DatabaseManager {

  public MySQLManager() {
    this("64.227.36.253","3306","seg","seg23","exw3karpouziastinakri", Table.click_table.toString(), Table.impression_table.toString(), Table.server_table.toString(), Table.campaign_table.toString());
//    this("64.227.36.253","3306","seg2020","seg23dev","12345", Table.click_table.toString(), Table.impression_table.toString(), Table.server_table.toString(), Table.campaign_table.toString());

  }

  public MySQLManager(final String host, final String port, final String db, final String user, final String pw) {
    super();

    sqlDatabase = new Database(host, port, db, user, pw);

    click_table = Table.click_table.toString();
    impression_table = Table.impression_table.toString();
    server_table = Table.server_table.toString();
    campaign_table = Table.campaign_table.toString();

    if (sqlDatabase.getConnection() == null) {
      Logger.log("Cannot establish database connection. Exiting now.");
      return;
    }
    Logger.log("Database connection established.");
    open = true;
    verifyDatabaseTables();
  }

  public MySQLManager(
      final String host,
      final String port,
      final String db,
      final String user,
      final String pw,
      final String c_table,
      final String i_table,
      final String s_table,
      final String camp_table) {
    this(host, port, db, user, pw);
  }

  public List<List<String>> retrieve(String statement, Object[] params, String[] resultColumns) {
    System.out.println(statement);
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<List<String>> results = new ArrayList<>();
    try {
      stmt = sqlDatabase.getConnection().prepareStatement(statement);

      for (int i = 0; i < params.length; i++) {
        if (params[i] instanceof Byte)
          stmt.setByte(i+1, (byte) params[i]);
        else if (params[i] instanceof Long)
          stmt.setLong(i+1, (long) params[i]);
        else if (params[i] instanceof String)
          stmt.setString(i+1, (String) params[i]);
        else if (params[i] instanceof java.sql.Date)
          stmt.setDate(i+1, (Date) params[i]);
        else
          System.err.println("Type not accounted for");
      }

      rs = stmt.executeQuery();
      while (rs.next()) {
        List<String> result = new ArrayList<>();
        for (String col : resultColumns)
          result.add(rs.getString(col));
        results.add(result);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(rs);
      DbUtils.closeQuietly(stmt);
    }
    return results;
  }

  private HashMap<String, Long> toLongMap(List<List<String>> resultsList) {
    HashMap<String, Long> resultMap = new LinkedHashMap<>();
    for (List<String> result : resultsList)
      if (result.size() != 2)
        Logger.log("[ERROR] Invalid result returned from SQL query. Expected 2 columns, received " + result.size() + ".");
      else
        try {
          resultMap.put(result.get(0), Long.valueOf(result.get(1)));
        } catch (NumberFormatException e) {
          Logger.log("[ERROR] Invalid result returned from SQL query. Long conversion failed on value <" + result.get(1) + ">.");
        }
    return resultMap;
  }

  private HashMap<String, BigDecimal> toBigDecimalMap(List<List<String>> resultsList) {
    HashMap<String, BigDecimal> resultMap = new LinkedHashMap<>();
    for (List<String> result : resultsList)
      if (result.size() != 2)
        Logger.log("[ERROR] Invalid result returned from SQL query. Expected 2 columns, received " + result.size() + ".");
      else
        try {
          resultMap.put(result.get(0), BigDecimal.valueOf(Double.parseDouble(result.get(1))));
        } catch (NumberFormatException e) {
          Logger.log("[ERROR] Invalid result returned from SQL query. Double conversion failed on value <" + result.get(1) + ">.");
        }
    return resultMap;
  }

  private long toLong(List<List<String>> resultsList) {
    if (resultsList.size() != 1)
      Logger.log("[ERROR] Invalid result returned from SQL query. Expected 1 columns, received " + resultsList.size() + ".");
    else if (resultsList.get(0).size() != 1)
      Logger.log("[ERROR] Invalid result returned from SQL query. Expected 1 result, received " + resultsList.get(0).size() + ".");
    else
      try {
        if (resultsList.get(0).get(0) == null) return 0L;
        else return Long.parseLong(resultsList.get(0).get(0));
      } catch (NumberFormatException e) {
        Logger.log("[ERROR] Invalid result returned from SQL query. Long conversion failed on value <" + resultsList.get(0).get(0) + ">.");
      }
    return 0L;
  }

  private BigDecimal toBigDecimal(List<List<String>> resultsList) {
    if (resultsList.size() != 1)
      Logger.log("[ERROR] Invalid result returned from SQL query. Expected 1 columns, received " + resultsList.size() + ".");
    else if (resultsList.get(0).size() != 1)
      Logger.log("[ERROR] Invalid result returned from SQL query. Expected 1 result, received " + resultsList.get(0).size() + ".");
    else
      try {
        if (resultsList.get(0).get(0) == null) return BigDecimal.ZERO;
        else return BigDecimal.valueOf(Double.parseDouble(resultsList.get(0).get(0)));
      } catch (NumberFormatException e) {
        Logger.log("[ERROR] Invalid result returned from SQL query. Double conversion failed on value <" + resultsList.get(0).get(0) + ">.");
      }
    return BigDecimal.ZERO;
  }

  @Override
  public void verifyDatabaseTables() {
    Logger.log("Verifying database tables...");
    boolean valid = true;
    if (!sqlDatabase.tableExists("credentials")) {
      Logger.log("Credentials table doesn't exist.");
      valid = false;
    }
    if (!sqlDatabase.tableExists(click_table)) {
      Logger.log("Click table doesn't exist.");
      valid = false;
    }
    if (!sqlDatabase.tableExists(impression_table)) {
      Logger.log("Impression table doesn't exist.");
      valid = false;
    }
    if (!sqlDatabase.tableExists(server_table)) {
      Logger.log("Server table doesn't exist.");
      valid = false;
    }
    if (!sqlDatabase.tableExists(campaign_table)) {
      Logger.log("Server table doesn't exist.");
      valid = false;
    }
    if(valid) {
      Logger.log("Verification complete.");
    }
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
    String where = filterToWhere(filter, (type.equals(Cost.Click_Cost) ? Table.click_table : Table.impression_table));
    String statement = "SELECT SUM(" + type.toString() + ") AS SUM " +
            "FROM " + (type.equals(Cost.Click_Cost) ? click_table : impression_table) +
            ( where.isEmpty() ? "" : " WHERE " + where );
    return toBigDecimal(
            retrieve(statement, new Object[]{}, new String[]{"SUM"})
    );
  }

  /**
   * Retrieve the number of bounces by time
   * @param maxSeconds the maximum time in seconds for which a bounce is registered
   * @param allowInf whether entries with no exit time will be counted
   * @return long value of the number of bounces
   */
  @Override
  public long retrieveBouncesCountByTime(long maxSeconds, boolean allowInf, Filter filter) {
    String where = filterToWhere(filter, Table.server_table);
    String statement = "SELECT COUNT(*) AS COUNT " +
            "FROM " + server_table +
            "WHERE " + ( where.isEmpty() ? "" : where + " AND ") + "((Exit_Date - Entry_Date) <= ?" + (allowInf ? " OR Exit_Date IS NULL)" : ")");
    return toLong(
            retrieve(statement, new Object[]{maxSeconds}, new String[]{"COUNT"})
    );
  }

  /**
   * Retrieve the number of bounces by number of pages visited
   * @param maxPages the maximum pages visited for which a bounce is registered
   * @return long value of the number of bounces
   */
  @Override
  public long retrieveBouncesCountByPages(byte maxPages, Filter filter) {
    String where = filterToWhere(filter, Table.server_table);
    String statement = "SELECT COUNT(*) AS COUNT " +
            "FROM " + server_table +
            " WHERE " + ( where.isEmpty() ? "" : where + " AND ") + "Pages_Viewed <= ?";
    return toLong(
            retrieve(statement, new Object[]{maxPages}, new String[]{"COUNT"})
    );
  }

  /**
   * Retrieve the total number of acquisitions
   * @return total count
   */
  public long retrieveAcquisitionCount(Filter filter) {
    String where = filterToWhere(filter, Table.server_table);
    String statement = "SELECT COUNT(*) AS COUNT " +
            "FROM " + server_table +
            " WHERE " + ( where.isEmpty() ? "" : where + " AND ") + "Conversion = 1";
    return toLong(
            retrieve(statement, new Object[]{}, new String[]{"COUNT"})
    );
  }

  /**
   * Retrieve the average acquisition cost.
   * @return the calculated average acquisition cost
   */
  @Override
  public BigDecimal retrieveAverageAcquisitionCost(Filter filter) {
    //TODO make more efficent
    String where = filterToWhere(filter, Table.click_table);
    String statement = "SELECT AVG(Click_Cost) AS AVG " +
            "FROM " + click_table +
            " WHERE " + ( where.isEmpty() ? "" : where + " AND ") + "ID IN (SELECT DISTINCT ID FROM " + server_table + " WHERE Conversion = 1)";
    return toBigDecimal(
            retrieve(statement, new Object[]{}, new String[]{"AVG"})
    );
  }

  /**
   * Retrieve the average click cost for each date.
   * @return a map with each date as keys and the avg for that date as a value
   */
  @Override
  public HashMap<String, BigDecimal> retrieveDatedAverageCost(Cost type, byte hoursGranularity, Filter filter) {
    Table table = (type.equals(Cost.Click_Cost) ? Table.click_table : Table.impression_table);
    String where = filterToWhere(filter, table);
    String cas = hoursToCase(hoursGranularity, table);
    String statement = "SELECT groups.start AS START, AVG(groups." + type.toString() + ") AS AVG " +
            "FROM (" +
            "SELECT " + type.toString() + ", CASE " + cas + " END AS start " +
            "FROM " + (type.equals(Cost.Click_Cost) ? click_table : impression_table) +
            ( where.isEmpty() ? "" : " WHERE " + where ) + ") groups " +
            "GROUP BY START";
    return toBigDecimalMap(
            retrieve(statement, new Object[]{}, new String[]{"START", "AVG"})
    );
  }

  /**
   * Retrieve the average acquisition cost for each date.
   * @return a map with each date as keys and the avg for that date as a value
   */
  @Override
  public HashMap<String, BigDecimal> retrieveDatedAverageAcquisitionCost(byte hoursGranularity, Filter filter) {
    String where = filterToWhere(filter, Table.click_table);
    String cas = hoursToCase(hoursGranularity, Table.click_table);
    String statement = "SELECT groups.start AS START, AVG(groups.Click_Cost) AS AVG " +
            "FROM (" +
            "SELECT Click_Cost, CASE " + cas + " END AS start " +
            "FROM " + click_table + " " +
            "WHERE ID IN (SELECT DISTINCT ID FROM " + server_table + " WHERE Conversion = 1) " + ( where.isEmpty() ? "" : " AND " + where) + ") groups " +
            "GROUP BY START";
    return toBigDecimalMap(
            retrieve(statement, new Object[]{}, new String[]{"START", "AVG"})
    );
  }


  /**
   * Retrieve the total number of impressions for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedImpressionTotals(byte hoursGranularity, Filter filter) {
    String where = filterToWhere(filter, Table.impression_table);
    String cas = hoursToCase(hoursGranularity, Table.impression_table);
    String statement = "SELECT groups.start AS START, COUNT(*) AS COUNT " +
            "FROM (" +
              "SELECT CASE " + cas + " END AS start " +
              "FROM " + impression_table + ( where.isEmpty() ? "" : " WHERE " + where ) + ") groups " +
            "GROUP BY START";
    return toLongMap(
            retrieve(statement, new Object[]{}, new String[]{"START", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of clicks for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedClickTotals(byte hoursGranularity, Filter filter) {
    String where = filterToWhere(filter, Table.click_table);
    String cas = hoursToCase(hoursGranularity, Table.click_table);
    String statement = "SELECT groups.start AS START, COUNT(*) AS COUNT " +
            "FROM (" +
            "SELECT CASE " + cas + " END AS start " +
            "FROM " + click_table + ( where.isEmpty() ? "" : " WHERE " + where ) + ") groups " +
            "GROUP BY START";
    return toLongMap(
            retrieve(statement, new Object[]{}, new String[]{"START", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of uniques for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedUniqueTotals(byte hoursGranularity, Filter filter) {
    String where = filterToWhere(filter, Table.click_table);
    String cas = hoursToCase(hoursGranularity, Table.click_table);
    String statement = "SELECT groups.start AS START, COUNT(DISTINCT groups.ID) AS COUNT " +
            "FROM (" +
            "SELECT ID, CASE " + cas + " END AS start " +
            "FROM " + click_table + ( where.isEmpty() ? "" : " WHERE " + where ) + ") groups " +
            "GROUP BY START";
    return toLongMap(
            retrieve(statement, new Object[]{}, new String[]{"START", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of bounces (by time) for each date.
   * @param maxSeconds the maximum time in seconds for which a bounce is registered
   * @param allowInf whether entries with no exit time will be counted
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedBounceTotalsByTime(byte hoursGranularity, long maxSeconds, boolean allowInf, Filter filter) {
    String where = filterToWhere(filter, Table.server_table);
    String cas = hoursToCase(hoursGranularity, Table.server_table);
    String statement = "SELECT groups.start AS START, COUNT(*) AS COUNT " +
            "FROM (" +
            "SELECT CASE " + cas + " END AS start " +
            "FROM " + server_table + " " +
            "WHERE (Exit_Date - Entry_Date) <= ?" + (allowInf ? " OR Exit_Date IS NULL" : "") + ( where.isEmpty() ? "" : " AND " + where ) + ") groups " +
            "GROUP BY START";
    return toLongMap(
            retrieve(statement, new Object[]{maxSeconds}, new String[]{"START", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of bounces (by pages visited) for each date.
   * @param maxPages the maximum pages visited for which a bounce is registered
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedBounceTotalsByPages(byte hoursGranularity, byte maxPages, Filter filter) {
    String where = filterToWhere(filter, Table.server_table);
    String cas = hoursToCase(hoursGranularity, Table.server_table);
    String statement = "SELECT groups.start AS START, COUNT(*) AS COUNT " +
            "FROM (" +
            "SELECT CASE " + cas + " END AS start " +
            "FROM " + server_table + " " +
            "WHERE Pages_Viewed <= ?" + ( where.isEmpty() ? "" : " AND " + where ) + ") groups " +
            "GROUP BY START";
    return toLongMap(
            retrieve(statement, new Object[]{maxPages}, new String[]{"START", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of acquisitions for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedAcquisitionTotals(byte hoursGranularity, Filter filter) {
    String where = filterToWhere(filter, Table.server_table);
    String cas = hoursToCase(hoursGranularity, Table.server_table);
    String statement = "SELECT groups.start AS START, COUNT(*) AS COUNT " +
            "FROM (" +
            "SELECT CASE " + cas + " END AS start " +
            "FROM " + server_table + " " +
            "WHERE Conversion = 1" + ( where.isEmpty() ? "" : " AND " + where ) + ") groups " +
            "GROUP BY START";
    return toLongMap(
            retrieve(statement, new Object[]{}, new String[]{"START", "COUNT"})
    );
  }

  /**
   * Retrieve demographics with count
   * @param type the type of demographics to retrieve
   * @return a map with each demographic as keys and the count for that demographic as a value
   */
  @Override
  public HashMap<String, Long> retrieveDemographics(Demographic type, Filter filter) {
    String where = filterToWhere(filter, Table.impression_table);
    PreparedStatement stmt = null;
    ResultSet rs = null;
    HashMap<String, Long> resultMap = new LinkedHashMap<>();
    try {
      StringBuilder sb = new StringBuilder("SELECT COUNT(*), ");
      sb.append(type.toString());
      sb.append(" FROM ");
      sb.append(impression_table);
      sb.append(( where.isEmpty() ? "" : " WHERE " + where ));
      sb.append(" GROUP BY ");
      sb.append(type.toString());
      stmt = sqlDatabase.getConnection().prepareStatement(sb.toString());
      rs = stmt.executeQuery();
      while (rs.next()) {
        String key = Demographics.getDemographicString(type, rs.getByte(type.toString()));
        resultMap.put(key, rs.getLong("COUNT(*)"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(rs);
      DbUtils.closeQuietly(stmt);
    }
    return resultMap;
  }

  /**
   * Retrieve the amount of entries in the specified database table
   * @param table the Table to check
   * @return the amount of entries found
   */
  public long retrieveDataCount(Table table, boolean unique, Filter filter) {
    String where = filterToWhere(filter, table);
    String statement = "SELECT COUNT(" + (unique ? "DISTINCT ID" : "*") + ") AS COUNT " +
            "FROM " + table +
            ( where.isEmpty() ? "" : " WHERE " + where );
    return toLong(
            retrieve(statement, new Object[]{}, new String[]{"COUNT"})
    );
  }

  public String retrieveCampaignName(int campaignID){
    String statement = "SELECT Name " +
        "FROM " + campaign_table +
        " WHERE Campaign_ID = " + campaignID;
    return retrieve(statement, new Object[]{}, new String[]{"Name"}).get(0).get(0);
  }

  /**
   * Attempt to login using given credentials and create UserSession
   * @param username the username to use during login
   * @param password the password to use during login
   * @return true if login is successful, false otherwise
   */
  @Override
  public boolean attemptUserLogin(String username, String password) {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = sqlDatabase.getConnection().prepareStatement("SELECT * FROM credentials WHERE username = ? LIMIT 1");
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if(!rs.next()) {
        return false;
      }
      if(Security.matchPassword(password, rs.getBytes("password"), rs.getBytes("salt"))) {
        String campaigns = rs.getString("campaigns");
        boolean access = rs.getBoolean("full_access");
        UserSession.initializeSession(username, campaigns, access);
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(rs);
      DbUtils.closeQuietly(stmt);
    }
    return false;
  }

  private static String filterToWhere(Filter filter, Table table) {
    String dateTitle = "Date";
    if (table.equals(Table.server_table)) dateTitle = "Entry_Date";

    String where = "";
    where += (filter.startDate != null ?                                    "DATE(" + dateTitle + ") >= '" + filter.startDate.toString() + "'" : "");
    where += (filter.endDate   != null ? (where.isEmpty() ? "" : " AND ") + "DATE(" + dateTitle + ") <= '" + filter.endDate.toString()   + "'" : "");

    String ID = "";
    ID += (filter.gender  >= 0 ?                                 "Gender = "  + filter.gender  : "");
    ID += (filter.age     >= 0 ? (ID.isEmpty() ? "" : " AND ") + "Age = "     + filter.age     : "");
    ID += (filter.income  >= 0 ? (ID.isEmpty() ? "" : " AND ") + "Income = "  + filter.income  : "");
    ID += (filter.context >= 0 ? (ID.isEmpty() ? "" : " AND ") + "Context = " + filter.context : "");

    if (!table.equals(Table.impression_table))
      ID = (ID.isEmpty() ? ID : "ID IN (SELECT DISTINCT ID FROM " + Table.impression_table + " WHERE " + ID + ")");
    where = (where.isEmpty() ? (ID.isEmpty() ? "" : ID) : (ID.isEmpty() ? where : where + " AND " + ID));
    where += (where.isEmpty() ? "Campaign_ID = "  + filter.getCampaignID() : " AND Campaign_ID = " + filter.getCampaignID());
    return where;
  }

  private static String hoursToCase(byte hoursGranularity, Table table) {
    String dateTitle = "Date";
    if (table.equals(Table.server_table)) dateTitle = "Entry_Date";

    switch (hoursGranularity) {
      case 6 :
        return "WHEN TIME("+dateTitle+") BETWEEN '00:00:00' AND '05:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 00:00:00') " +
                "WHEN TIME("+dateTitle+") BETWEEN '06:00:00' AND '11:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 06:00:00') " +
                "WHEN TIME("+dateTitle+") BETWEEN '12:00:00' AND '17:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 12:00:00') " +
                "WHEN TIME("+dateTitle+") BETWEEN '18:00:00' AND '23:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 18:00:00')";
      case 12 :
        return "WHEN TIME("+dateTitle+") BETWEEN '00:00:00' AND '11:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 00:00:00') " +
                "WHEN TIME("+dateTitle+") BETWEEN '12:00:00' AND '23:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 12:00:00')";
      default :
        return "WHEN TIME("+dateTitle+") BETWEEN '00:00:00' AND '23:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 00:00:00')";
    }
  }
}