package com.comp2211.dashboard.io;

import com.comp2211.dashboard.GUIStarter;
import com.comp2211.dashboard.io.DatabaseManager.Table;
import com.comp2211.dashboard.util.UserSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataImporter {

  private DatabaseManager dbManager;

  public DataImporter(DatabaseManager dbManager) {
    this.dbManager = dbManager;
  }

  public void startImport(String title, File impression, File click, File server) throws SQLException {
    int campaignID = createCampaign(title); // set campaign id
    importData(impression, Table.impression_table, "Date, ID, Gender, Age, Income, Context, Impression_Cost, Campaign_ID", campaignID);
    importData(click, Table.click_table,"Date, ID, Click_Cost, Campaign_ID", campaignID);
    importData(server, Table.server_table,"Entry_Date, ID, Exit_Date, Pages_Viewed, Conversion, Campaign_ID", campaignID);
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
    ArrayList<String[]> list = readCSV(file);
    String sql = "INSERT INTO " + table.toString() + " (" + columnNames + ") VALUES ";
    StringBuilder sb = new StringBuilder(sql);
    String seperator = "";
    for (String[] record : list) {
      sb.append(seperator + "(");
      seperator = ",";
      for (String value : record) {
        value = value.toLowerCase();
        if (value.contains(":")) {
          value = parseDate(value);
        }
        if(value.equals("n/a")){
          sb.append("NULL,");
          continue;
        }
        if (value.equals("male") || value.equals("low") || value.equals("blog") || value.equals("no") || value.equals("<25")){
          value = "0";
        }
        if (value.equals("female") || value.equals("medium") || value.equals("yes") || value.equals("news") || value.equals("25-34")){
          value = "1";
        }
        if (value.equals("high") || value.equals("shopping")|| value.equals("35-44")){
          value = "2";
        }
        if (value.equals("social media")|| value.equals("45-54")){
          value = "3";
        }
        if(value.equals(">54")){
          value = "4";
        }
        sb.append("'" + value + "'" + ",");
      }
      sb.append(campaignID + ")");
    }
    sql = sb.toString();
    PreparedStatement statement = GUIStarter.getDatabaseManager().sqlDatabase.getConnection().prepareStatement(sql);
    System.out.println(statement);
    statement.executeUpdate();
  }

  private String parseDate(String value) {
    String dd = value.substring(0, 2);
    String mm = value.substring(3, 5);
    String yyyy = value.substring(6, 10);
    return yyyy + "-" + mm + "-" + dd + value.substring(10);
  }

  private ArrayList<String[]> readCSV(File file) {
    String line = "";
    ArrayList<String[]> list = new ArrayList<>();
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
    list.remove(0);
    return list;
  }
}
