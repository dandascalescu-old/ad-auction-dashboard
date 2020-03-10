package com.comp2211.dashboard.util;

import java.util.ArrayList;
import java.util.List;
import com.comp2211.dashboard.Campaign;

public class UserSession {
  protected static List<Campaign> campaigns;
  protected static boolean fullAccess;
  protected static String username;
  protected static boolean valid = false;

  /**
   * Stores information about the user session
   * @param dbName the name of the user
   * @param dbCampaigns the list of campaigns the user will be able to access
   * @param dbAccess whether the user has full access
   */
  public static void initializeSession(String dbName, String dbCampaigns, boolean dbAccess) {
    username = dbName;
    fullAccess = dbAccess;
    campaigns = new ArrayList<>();
    for(String s : dbCampaigns.split(";")) {
      Campaign c = Campaign.getCampaignByID(s);
      if (c != null) {
        campaigns.add(c);
      }
    }
    
    if (!campaigns.isEmpty()) {
      valid = true;
    }
  }
  
  public static String getUsername() {
    return username;
  }

  public static List<Campaign> getAllowedCampaigns() {
    return campaigns;
  }

  public static boolean hasFullAccess() {
    return fullAccess;
  }

  public static void clearSession() {
    campaigns.clear();
    fullAccess = false;
    valid = false;
  }
  
  public static boolean isValid() {
    return valid;
  }
}
