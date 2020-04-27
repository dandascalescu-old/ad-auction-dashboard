package com.comp2211.dashboard.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.GUIStarter;
import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.io.MockDatabaseManager;
import java.util.Iterator;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrimaryViewModelTest {

  private static DatabaseManager manager;
  private PrimaryViewModel viewModel;

  @BeforeEach
  void setUp() {
    this.manager = new MockDatabaseManager();

    // Instantiates Campaign singleton with multiple campaigns
    new Campaign("Demo Campaign 1", this.manager);
    new Campaign("Demo Campaign 2", this.manager);

    this.viewModel = new PrimaryViewModel();
    this.viewModel.initialize();
  }

  @AfterEach
  void tearDown() {
    this.viewModel = null;
    assertNull(this.viewModel);
  }

  @Test
  void campaignsList() {
    Boolean listMatches = true;

    int i = 0;
    for (Iterator<Campaign> it = this.viewModel.campaignsList().iterator(); it.hasNext(); i++) {
      Campaign c = it.next();
      if (!c.getCampaignID().equals(Campaign.getCampaigns().get(i).getCampaignID())) {
        listMatches = false;
        break;
      }
    }

    assertTrue(listMatches);

    System.out.println("this.viewModel.campaignsList().size(): " + this.viewModel.campaignsList().size());
    assertTrue(this.viewModel.campaignsList().size() == 2);
  }

//  @Test
//  void totalList() {}
//
//  @Test
//  void averagesList() {}
//
//  @Test
//  void demographicsList() {}
//
//  @Test
//  void averageChartData() {}
//
//  @Test
//  void totalMetricChartData() {}
//
//  @Test
//  void demographicsChartData() {}
//
//  @Test
//  void selectedAverageProperty() {}
//
//  @Test
//  void selectedTotalMetricProperty() {}
//
//  @Test
//  void selectedDemographicProperty() {}
//
//  @Test
//  void selectedCampaignProperty() {}
//
//  @Test
//  void totalClickCostProperty() {}
//
//  @Test
//  void totalImpresCostProperty() {}
//
//  @Test
//  void totalCostProperty() {}
//
//  @Test
//  void clickThroughRateTextProperty() {}
//
//  @Test
//  void bounceConversionTextProperty() {}
//
//  @Test
//  void bounceRateTextProperty() {}
//
//  @Test
//  void getConversionUniquesProperty() {}
//
//  @Test
//  void getTotalImpressionsProperty() {}
//
//  @Test
//  void getTotalClicksProperty() {}
//
//  @Test
//  void getTotalUniquesProperty() {}
//
//  @Test
//  void getTotalBouncesProperty() {}
//
//  @Test
//  void getTotalConversionsProperty() {}
}