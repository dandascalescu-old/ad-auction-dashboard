package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.GUIStarter;
import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.util.Security;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import de.saxsys.mvvmfx.utils.notifications.DefaultNotificationCenter;


public class LoginViewModel implements ViewModel {

  private final DatabaseManager dbManager;

  public final static String SHOW_AUTHENTICATED_VIEW = "SHOW_AUTHENTICATED_VIEW";

  public StringProperty usernameString = new SimpleStringProperty("");
  public StringProperty passwordString = new SimpleStringProperty("");

  private Command loginCommand;

  public LoginViewModel() {
    dbManager = GUIStarter.getDatabaseManager();

    loginCommand = new DelegateCommand(() -> new Action() {
      @Override
      protected void action() throws Exception {
        login();
      }
    });
  }

  public Command getLoginCommand() {
    return loginCommand;
  }

  public StringProperty usernameStringProperty() {
    return usernameString;
  }

  public StringProperty passwordStringProperty() {
    return passwordString;
  }

  private void login() {
    String username = usernameString.get().trim();
    String password = passwordString.get().trim();

    if(!Security.validateText(username)) {
      Logger.log("Username can only alphanumeric characters, try again.");
      return;
    }

    if(!dbManager.attemptUserLogin(username, password)) {
      Logger.log("Credentials not recognized, try again.");
      return;
    }
    // TODO: get campaigns from UserSession
    Campaign campaign = new Campaign("Demo Campaign", GUIStarter.getDatabaseManager());
    campaign.cacheData();

    showAuthenticatedView();
    return;
  }

  public void showAuthenticatedView() {
    publish(SHOW_AUTHENTICATED_VIEW, "Logged in");
  }

}
