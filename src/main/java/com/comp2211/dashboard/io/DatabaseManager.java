package com.comp2211.dashboard.io;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseManager {

  public static Database sqlDatabase;
  private static boolean open = false;

  /**
   * Initialise the database connection using info from the configuration file
   */
  public static void init() {
    sqlDatabase = new Database("localhost","3306","seg2020","seg","pass");

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
