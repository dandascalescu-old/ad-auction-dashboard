package com.comp2211.dashboard;

import com.comp2211.dashboard.io.MySQLManager;
import com.comp2211.dashboard.view.App;

public class GUIStarter {
  
  private static MySQLManager manager;
  
  public static void main(final String[] args) {
    manager = new MySQLManager();
    if(GUIStarter.getDatabaseManager().isOpen()) {
      App.main();
    }
  }
  
  public static MySQLManager getDatabaseManager() {
    return manager;
  }
}