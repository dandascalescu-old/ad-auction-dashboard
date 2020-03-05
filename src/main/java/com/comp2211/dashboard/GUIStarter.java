package com.comp2211.dashboard;

import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.view.App;

public class GUIStarter {

  public static void main(final String[] args) {
    DatabaseManager.init();
    App.main();
  }
}