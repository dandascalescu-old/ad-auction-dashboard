package com.comp2211.dashboard.io;

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
    super("64.227.36.253","3306","seg","seg23","exw3karpouziastinakri", Table.click_table.toString(), Table.impression_table.toString(), Table.server_table.toString());
  }

  public MySQLManager(final String host, final String port, final String db, final String user, final String pw) {
    super( host, port, db, user, pw, Table.click_table.toString(), Table.impression_table.toString(), Table.server_table.toString());
  }

  public MySQLManager(final String host, final String port, final String db, final String user, final String pw, final String c_table, final String i_table, final String s_table) {
    super(host, port, db, user, pw, c_table, i_table, s_table);
  }

  public List<List<String>> retrieve(String statement, Object[] params, String[] resultColumns) {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<List<String>> results = new ArrayList<>();
    try {
      stmt = sqlDatabase.getConnection().prepareStatement(statement);
      //System.out.println(sb.toString());//test

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
        return Long.parseLong(resultsList.get(0).get(0));
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
        return BigDecimal.valueOf(Double.parseDouble(resultsList.get(0).get(0)));
      } catch (NumberFormatException e) {
        Logger.log("[ERROR] Invalid result returned from SQL query. Double conversion failed on value <" + resultsList.get(0).get(0) + ">.");
      }
    return BigDecimal.ZERO;
  }

  /**
   * Retrieve the total for the specified type.
   * @param type the type of cost to retrieve.
   * @return BigDecimal representing the total cost.
   */
  @Override
  public BigDecimal retrieveTotalCost(Cost type) {
    String statement = "SELECT SUM(" + type.toString() + ") AS SUM " +
            "FROM " + (type.equals(Cost.Click_Cost) ? click_table : impression_table);
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
  public long retrieveBouncesCountByTime(long maxSeconds, boolean allowInf) {
    String statement = "SELECT COUNT(*) AS COUNT " +
            "FROM " + server_table +
            "WHERE (Exit_Date - Entry_Date) <= ?" + (allowInf ? " OR Exit_Date IS NULL" : "");
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
  public long retrieveBouncesCountByPages(byte maxPages) {
    String statement = "SELECT COUNT(*) AS COUNT " +
            "FROM " + server_table +
            " WHERE Pages_Viewed <= ?";
    return toLong(
            retrieve(statement, new Object[]{maxPages}, new String[]{"COUNT"})
    );
  }

  /**
   * Retrieve the total number of acquisitions
   * @return total count
   */
  public long retrieveAcquisitionCount() {
    String statement = "SELECT COUNT(*) AS COUNT " +
            "FROM " + server_table +
            " WHERE Conversion = 1";
    return toLong(
            retrieve(statement, new Object[]{}, new String[]{"COUNT"})
    );
  }

  /**
   * Retrieve the average acquisition cost.
   * @return the calculated average acquisition cost
   */
  @Override
  public BigDecimal retrieveAverageAcquisitionCost() {
    String statement = "SELECT AVG(Click_Cost) AS AVG " +
            "FROM " + click_table +
            " WHERE ID IN (SELECT DISTINCT ID FROM " + server_table + " WHERE Conversion = 1)";
    return toBigDecimal(
            retrieve(statement, new Object[]{}, new String[]{"AVG"})
    );
  }

  /**
   * Retrieve the average click cost for each date.
   * @return a map with each date as keys and the avg for that date as a value
   */
  @Override
  public HashMap<String, BigDecimal> retrieveDatedAverageCost(Cost type) {
    String statement = "SELECT DATE(Date) AS DateOnly, AVG(" + type.toString() + ") AS AVG " +
            "FROM " + (type.equals(Cost.Click_Cost) ? click_table : impression_table) +
            " GROUP BY DateOnly";
    return toBigDecimalMap(
            retrieve(statement, new Object[]{}, new String[]{"DateOnly", "AVG"})
    );
  }

  /**
   * Retrieve the average acquisition cost for each date.
   * @return a map with each date as keys and the avg for that date as a value
   */
  @Override
  public HashMap<String, BigDecimal> retrieveDatedAverageAcquisitionCost() {
    String statement = "SELECT DATE(Date) AS DateOnly, AVG(Click_Cost) AS AVG " +
            "FROM " + click_table +
            " WHERE ID IN (SELECT DISTINCT ID FROM " + server_table + " WHERE Conversion = 1) " +
            "GROUP BY DateOnly";
    return toBigDecimalMap(
            retrieve(statement, new Object[]{}, new String[]{"DateOnly", "AVG"})
    );
  }


  /**
   * Retrieve the total number of impressions for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedImpressionTotals() {
    String statement = "SELECT DATE(Date) AS DateOnly, COUNT(*) AS COUNT " +
            "FROM " + impression_table +
            " GROUP BY DateOnly";
    return toLongMap(
            retrieve(statement, new Object[]{}, new String[]{"DateOnly", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of clicks for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedClickTotals() {
    String statement = "SELECT DATE(Date) AS DateOnly, COUNT(*) AS COUNT " +
            "FROM " + click_table +
            " GROUP BY DateOnly";
    return toLongMap(
            retrieve(statement, new Object[]{}, new String[]{"DateOnly", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of uniques for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedUniqueTotals() {
    String statement = "SELECT DATE(Date) AS DateOnly, COUNT(DISTINCT ID) AS COUNT " +
            "FROM " + click_table +
            " GROUP BY DateOnly";
    return toLongMap(
            retrieve(statement, new Object[]{}, new String[]{"DateOnly", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of bounces (by time) for each date.
   * @param maxSeconds the maximum time in seconds for which a bounce is registered
   * @param allowInf whether entries with no exit time will be counted
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedBounceTotalsByTime(long maxSeconds, boolean allowInf) {
    String statement = "SELECT DATE(Entry_Date) AS DateOnly, COUNT(*) AS COUNT " +
            "FROM " + server_table +
            " WHERE (Exit_Date - Entry_Date) <= ?" + (allowInf ? " OR Exit_Date IS NULL" : "") +
            " GROUP BY DateOnly";
    return toLongMap(
            retrieve(statement, new Object[]{maxSeconds}, new String[]{"DateOnly", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of bounces (by pages visited) for each date.
   * @param maxPages the maximum pages visited for which a bounce is registered
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedBounceTotalsByPages(byte maxPages) {
    String statement = "SELECT DATE(Entry_Date) AS DateOnly, COUNT(*) AS COUNT " +
            "FROM " + server_table +
            " WHERE Pages_Viewed <= ?" +
            " GROUP BY DateOnly";
    return toLongMap(
            retrieve(statement, new Object[]{maxPages}, new String[]{"DateOnly", "COUNT"})
    );
  }

  /**
   * Retrieve the total number of acquisitions for each date.
   * @return a map with each date as keys and the total for that date as a value
   */
  @Override
  public HashMap<String, Long> retrieveDatedAcquisitionTotals() {
    String statement = "SELECT DATE(Entry_Date) AS DateOnly, COUNT(*) AS COUNT " +
            "FROM " + server_table +
            " WHERE Conversion = 1" +
            " GROUP BY DateOnly";
    return toLongMap(
            retrieve(statement, new Object[]{}, new String[]{"DateOnly", "COUNT"})
    );
  }

  /**
   * Retrieve demographics with count
   * @param type the type of demographics to retrieve
   * @return a map with each demographic as keys and the count for that demographic as a value
   */
  @Override
  public HashMap<String, Long> retrieveDemographics(Demographic type) {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    HashMap<String, Long> resultMap = new LinkedHashMap<>();
    try {
      StringBuilder sb = new StringBuilder("SELECT COUNT(*), ");
      sb.append(type.toString());
      sb.append(" FROM ");
      sb.append(impression_table);
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
  @Override
  public long retrieveDataCount(String table, boolean unique) {
    String statement = "SELECT COUNT(" + (unique ? "DISTINCT ID" : "*") + ") AS COUNT " +
            "FROM " + table;
    return toLong(
            retrieve(statement, new Object[]{}, new String[]{"COUNT"})
    );
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
}
