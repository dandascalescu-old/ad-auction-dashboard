package com.comp2211.dashboard.io;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.GUIStarter;
import com.comp2211.dashboard.io.DatabaseManager.Table;
import com.comp2211.dashboard.model.data.Filter;
import com.comp2211.dashboard.util.UserSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

public class DataImporter {

  private DatabaseManager dbManager;

  public DataImporter(DatabaseManager dbManager) {
    this.dbManager = dbManager;
  }

  public Campaign startImport(String title, File impression, File click, File server) throws SQLException {
    int campaignID = createCampaign(title); // set campaign id
    Campaign c = new Campaign(campaignID, GUIStarter.getDatabaseManager().retrieveCampaignName(campaignID), GUIStarter.getDatabaseManager());
    importData(impression, Table.impression_table, "Date, ID, Gender, Age, Income, Context, Impression_Cost, Campaign_ID", campaignID);
    importData(click, Table.click_table,"Date, ID, Click_Cost, Campaign_ID", campaignID);
    importData(server, Table.server_table,"Entry_Date, ID, Exit_Date, Pages_Viewed, Conversion, Campaign_ID", campaignID);
    c.cacheData(new Filter(campaignID));
    return c;
  }

  private int createCampaign(String title) throws SQLException {
    String sql = "INSERT INTO campaign_table (Name) VALUES (?)";
    PreparedStatement statement =
        GUIStarter.getDatabaseManager()
            .sqlDatabase
            .getConnection()
            .prepareStatement(sql, new String[] {"Campaign_ID"});
    statement.setString(1, title);
    statement.executeUpdate();
    ResultSet rs = statement.getGeneratedKeys();
    if (rs.next()) {
      long campaignID = rs.getLong(1);

      //Update user to have access to this campaign
      String sql2 = "UPDATE credentials SET campaigns = CONCAT(campaigns,?) WHERE username = ?";
      PreparedStatement statement2 =
          GUIStarter.getDatabaseManager().sqlDatabase.getConnection().prepareStatement(sql2);
      statement2.setString(1, ";"+campaignID);
      statement2.setString(2, UserSession.getUsername());
      statement2.executeUpdate();

      return (int) campaignID;
    } else {
      System.out.println("failed to get campaign id");
      return -1;
    }
  }

  private void importData(File file, Table table, String columnNames, int campaignID) throws SQLException {
    LinkedList<String[]> list = readCSV(file);
    long startTime = System.currentTimeMillis();
    String sql = "INSERT INTO " + table.toString() + " (" + columnNames + ") VALUES ";
    StringBuilder sb = new StringBuilder(sql);
    String separator = "";
    for (String[] record : list) {
      sb.append(separator).append("(");
      separator = ",";
      for (String value : record) {
        value = value.toLowerCase();
        if (value.contains(":")) {
          value = parseDate(value);
        } else {
          switch (value) {
            case "n/a":
              sb.append("NULL,");
              continue;
            case "male":
            case "low":
            case "blog":
            case "no":
            case "<25":
              value = "0";
              break;
            case "female":
            case "medium":
            case "yes":
            case "news":
            case "25-34":
              value = "1";
              break;
            case "high":
            case "shopping":
            case "35-44":
              value = "2";
              break;
            case "social media":
            case "45-54":
              value = "3";
              break;
            case ">54":
              value = "4";
              break;
          }
        }
        sb.append("'").append(value).append("'").append(",");
      }
      sb.append(campaignID).append(")");
    }
    sql = sb.toString();
    PreparedStatement statement = GUIStarter.getDatabaseManager().sqlDatabase.getConnection().prepareStatement(sql);
    System.out.println("importData (build statement): "+ (System.currentTimeMillis() - startTime));
    startTime = System.currentTimeMillis();
    statement.executeUpdate();
    System.out.println("importData (execute statement): "+ (System.currentTimeMillis() - startTime));
  }

  private String parseDate(String value) {
//    String dd = value.substring(0, 2);
//    String mm = value.substring(3, 5);
//    String yyyy = value.substring(6, 10);
    return value.substring(6, 10) + "-" + value.substring(3, 5) + "-" + value.substring(0, 2) + value.substring(10);
  }

  private LinkedList<String[]> readCSV(File file) {
    long startTime = System.currentTimeMillis();
    String line = "";
    LinkedList<String[]> list = new LinkedList<>();
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new FileReader(file));
      while ((line = bufferedReader.readLine()) != null) {
        String[] record = line.split(",");
        list.add(record);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    list.removeFirst();
    System.out.println("readCSV: "+ (System.currentTimeMillis() - startTime));
    return list;
  }
}
