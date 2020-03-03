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
    testData();
  }

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
  
  public static String buildDataQuery(Table table, int limit) {
	String query = "SELECT * FROM " + table;
	if(limit > 0) {
	  query += " LIMIT " + limit;
	}
    return query + ";";
  }
  
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
  
  public static ArrayList<ServerData> retrieveServerData(int limit) {
	final ResultSet rs = sqlDatabase.readQuery(buildDataQuery(Table.server_table, limit));
	ArrayList<ServerData> dataList = new ArrayList<ServerData>(limit);
	try {
	  while (rs.next()) {
		dataList.add(new ServerData(rs.getString("ID"), rs.getTimestamp("Entry_Date"), rs.getTimestamp("Exit_Date"), (int) rs.getInt("Pages_Viewed"), rs.getBoolean("Conversion")));
	  }
    } catch (SQLException e) {
	   // TODO Auto-generated catch block
       e.printStackTrace();
	}
	return dataList;
  }
  
  public static void testData() {
    final ResultSet rs1 = sqlDatabase.readQuery("SELECT COUNT(Click_Cost) FROM click_table WHERE Click_Cost >= 10;");
    final ResultSet rs2 = sqlDatabase.readQuery("SELECT SUM(Click_Cost) FROM click_table WHERE Click_Cost >= 10;");
    try {
      if (rs1.next()) {
        System.out.println("Amount of rows with click_cost >= 10: " + rs1.getInt("COUNT(Click_Cost)"));
      }

      if (rs2.next()) {
        System.out.println("Total Cost from those rows (with individual click_cost >= 10): " + rs2.getBigDecimal("SUM(Click_Cost)"));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
