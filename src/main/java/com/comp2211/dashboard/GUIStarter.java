package com.comp2211.dashboard;

import com.comp2211.dashboard.io.MySQLManager;
import com.comp2211.dashboard.view.App;

public class GUIStarter {

  public static void main(final String[] args) {
    MySQLManager manager = new MySQLManager();
    if(manager.isOpen()) {
      new Campaign("Demo Campaign", manager);
      App.main();
    }
  }
}