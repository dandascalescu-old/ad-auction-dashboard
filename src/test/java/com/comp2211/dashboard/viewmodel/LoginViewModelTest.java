package com.comp2211.dashboard.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.io.MockDatabaseManager;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginViewModelTest {

  private LoginViewModel viewModel;

  @BeforeEach
  void setUp() {
    DatabaseManager dbManager = new MockDatabaseManager();
    this.viewModel = new LoginViewModel(dbManager);
  }

  @AfterEach
  void tearDown() {
    this.viewModel = null;
  }

  @Test
  @Description("Test correct login credentials")
  void getLoginCommandSuccess() {
    viewModel.subscribe(LoginViewModel.SHOW_AUTHENTICATED_VIEW, (key, payload) -> {
      assertTrue(true);
    });

    viewModel.subscribe(LoginViewModel.UNABLE_TO_AUTHENTICATE, (key, payload) -> {
      assertTrue(false);
    });

    viewModel.usernameStringProperty().setValue("test");
    viewModel.passwordStringProperty().setValue("test");

    viewModel.login();
  }

  @Test
  @Description("Test incorrect login credentials")
  void getLoginCommandFail() {
    viewModel.subscribe(LoginViewModel.SHOW_AUTHENTICATED_VIEW, (key, payload) -> {
      assertTrue(false);
    });

    viewModel.subscribe(LoginViewModel.UNABLE_TO_AUTHENTICATE, (key, payload) -> {
      assertTrue(true);
    });

    viewModel.usernameStringProperty().setValue("wuofw");
    viewModel.passwordStringProperty().setValue("invtest");

    viewModel.login();
  }
}