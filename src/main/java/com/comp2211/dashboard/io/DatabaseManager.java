package com.comp2211.dashboard.io;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.comp2211.dashboard.data.ClickData;
import com.comp2211.dashboard.data.ImpressionData;
import com.comp2211.dashboard.data.ServerData;


public class DatabaseManager {

  public static Database sqlDatabase;
  private static boolean open = false;
  
  public enum Table { click_table, impression_table, server_table };

  /**
   * Initialise the database connection using info from the configuration file
   */
  public static void init() {
    sqlDatabase = new Database("64.227.36.253","3306","seg2020","seg23","exw3karpouziastinakri");

    if (sqlDatabase.open() == null) {
      System.out.println("Database error occured. Exiting now.");
    }
    System.out.println("Database connection established.");
    open = true;
    verifyDatabaseTables();
  }

  /**
   * Verifies and prints if any database tables aren't available
   */
  public static void verifyDatabaseTables() {
    if (!sqlDatabase.tableExists("click_table")) {
      System.out.println("Click table doesn't exist.");
    }
    if (!sqlDatabase.tableExists("impression_table")) {
      System.out.println("Impression table doesn't exist.");
    }
    if (!sqlDatabase.tableExists("server_table")) {
      System.out.println("Server table doesn't exist.");
    }
  }

  /**
   * @return whether the database connection was opened successfully
   */
  public static boolean isOpen() {
    return open;
  }

  /**
   * Retrieve the total click cost using an SQL query
   * @return the calculated click cost
   */
  public static BigDecimal retrieveTotalClickCost() {
    final ResultSet rs = sqlDatabase.readQuery("SELECT SUM(Click_Cost) FROM click_table;");
    try {
      if (rs.next()) {
        return rs.getBigDecimal("SUM(Click_Cost)");
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return BigDecimal.valueOf(0);
  }
  
  /**
   * Retrieve the total impression cost using an SQL query
   * @return the calculated impression cost
   */
  public static BigDecimal retrieveTotalImpressionCost() {
    final ResultSet rs = sqlDatabase.readQuery("SELECT SUM(Impression_Cost) FROM impression_table;");
    try {
      if (rs.next()) {
        return rs.getBigDecimal("SUM(Impression_Cost)");
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return BigDecimal.valueOf(0);
  }

  /**
   * Retrieve the amount of entries in the specified database table
   * @param t the Table to check
   * @return the amount of entries found
   */
  public static long retrieveDataCount(Table t) {
	final ResultSet rs = sqlDatabase.readQuery("SELECT COUNT(*) FROM " + t.toString() + ";");
	try {
	  if (rs.next()) {
		return rs.getLong("COUNT(*)");
	  }
    } catch (SQLException e) {
	   // TODO Auto-generated catch block
       e.printStackTrace();
	}
	return 0L;
  }
  
  /**
   * Helper function to build a query with optional limit
   * @param table the table to query
   * @param limit the amount of entries to get, use 0 to select all entries.
   * @return the final query
   */
  public static String buildDataQuery(Table table, int limit) {
	String query = "SELECT * FROM " + table;
	if(limit > 0) {
	  query += " LIMIT " + limit;
	}
    return query + ";";
  }
  
  /**
   * Retrieves clickData entries from the database
   * @param limit the amount of entries to get, use 0 to select all entries.
   * @return a list of all the selected entries
   */
  public static ArrayList<ClickData> retrieveClickData(int limit) {
	final ResultSet rs = sqlDatabase.readQuery(buildDataQuery(Table.click_table, limit));
	ArrayList<ClickData> dataList = new ArrayList<ClickData>(limit);
	try {
	  while (rs.next()) {
		dataList.add(new ClickData(rs.getString("ID"), rs.getTimestamp("Date"), rs.getBigDecimal("Click_Cost")));
	  }
    } catch (SQLException e) {
	   // TODO Auto-generated catch block
       e.printStackTrace();
	}
	return dataList;
  }

  /**
   * Retrieves impressionData entries from the database
   * @param limit the amount of entries to get, use 0 to select all entries.
   * @return a list of all the selected entries
   */
  public static ArrayList<ImpressionData> retrieveImpressionData(int limit) {
	final ResultSet rs = sqlDatabase.readQuery(buildDataQuery(Table.impression_table, limit));
	ArrayList<ImpressionData> dataList = new ArrayList<ImpressionData>(limit);
	try {
	  while (rs.next()) {
		dataList.add(new ImpressionData(rs.getString("ID"), rs.getTimestamp("Date"), rs.getString("Age"), rs.getByte("Income"), rs.getByte("Context"), rs.getBigDecimal("Impression_Cost"), rs.getBoolean("Gender")));
	  }
    } catch (SQLException e) {
	   // TODO Auto-generated catch block
       e.printStackTrace();
	}
	return dataList;
  }
  
  /**
   * Retrieves serverData entries from the database
   * @param limit the amount of entries to get, use 0 to select all entries.
   * @return a list of all the selected entries
   */
  public static ArrayList<ServerData> retrieveServerData(int limit) {
	final ResultSet rs = sqlDatabase.readQuery(buildDataQuery(Table.server_table, limit));
	ArrayList<ServerData> dataList = new ArrayList<ServerData>(limit);
	try {
	  while (rs.next()) {
		dataList.add(new ServerData(rs.getString("ID"), rs.getTimestamp("Entry_Date"), rs.getTimestamp("Exit_Date"), rs.getByte("Pages_Viewed"), rs.getBoolean("Conversion")));
	  }
    } catch (SQLException e) {
	   // TODO Auto-generated catch block
       e.printStackTrace();
	}
	return dataList;
  }
}
