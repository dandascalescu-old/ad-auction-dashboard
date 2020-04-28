package com.comp2211.dashboard.viewmodel;

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


public class LoginViewModel implements ViewModel {

  private DatabaseManager dbManager;

  public final static String SHOW_AUTHENTICATED_VIEW = "SHOW_AUTHENTICATED_VIEW";
  public final static String UNABLE_TO_AUTHENTICATE = "UNABLE_TO_AUTHENTICATE";

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

  public LoginViewModel(DatabaseManager dbManager) {
    this();
    this.dbManager = dbManager;
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

  public void login() {
    String username = usernameString.get().trim();
    String password = passwordString.get().trim();

    if(!Security.validateText(username)) {
      Logger.log("Username can only alphanumeric characters, try again.");
      unableToAuthenticate();
      return;
    }

    if (!dbManager.attemptUserLogin(username, password)) {
      Logger.log("Credentials not recognized, try again.");
      unableToAuthenticate();
      return;
    }

    showAuthenticatedView();
    return;
  }

  public void showAuthenticatedView() {
    publish(SHOW_AUTHENTICATED_VIEW, "Logged in");
  }

  public void unableToAuthenticate() {
    publish(UNABLE_TO_AUTHENTICATE, "Unable to authenticate");
  }
}
