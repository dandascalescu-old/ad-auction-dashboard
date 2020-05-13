package com.comp2211.dashboard.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.io.MockDatabaseManager;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.model.data.Filter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrimaryViewModelTest {

  private static DatabaseManager manager;
  private PrimaryViewModel viewModel;

  private static int campaignCount = 2;

  @BeforeEach
  void setUp() {
    this.manager = new MockDatabaseManager();

    // Instantiates Campaign singleton with multiple campaigns
    Campaign c1 = new Campaign(1,"Demo Campaign 1", this.manager);
    c1.cacheData(new Filter(1));
    Campaign c2 = new Campaign(2, "Demo Campaign 2", this.manager);
    c2.cacheData(new Filter(1));


    this.viewModel = new PrimaryViewModel();
    this.viewModel.initialize();
  }

  @AfterEach
  void tearDown() {
    Campaign.removeAllCampaigns();
    this.viewModel = null;
    assertNull(this.viewModel);
  }

  @Test
  void campaignsList() {
    Boolean listMatches = true;

    int i = 0;
    for (Iterator<Campaign> it = this.viewModel.campaignsList().iterator(); it.hasNext(); i++) {
      Campaign c = it.next();
      if (!c.getCampaignName().equals(Campaign.getCampaigns().get(i).getCampaignName())) {
        listMatches = false;
        break;
      }
    }

    assertTrue(listMatches);

    assertEquals(this.campaignCount, this.viewModel.campaignsList().size());
  }

  @Test
  void totalList() {
    String[] totals = {"Impressions", "Clicks", "Uniques", "Bounces", "Conversions"};
    List<String> totalsList = Arrays.asList(totals);
    String totalNotAvailable = "";

    Iterator<String> it = this.viewModel.totalList().iterator();
    while (it.hasNext()) {
      String totalHeader = it.next();
      if (!totalsList.contains(totalHeader)) {
        totalNotAvailable = totalHeader;
        break;
      }
    }

    assertTrue(totalNotAvailable.isEmpty(), "Total header not shown: " + totalNotAvailable);
  }

  @Test
  void averagesList() {
    String[] averages = {"Impressions", "Clicks", "Conversions"};
    List<String> totalsList = Arrays.asList(averages);
    String avgNotAvailable = "";

    Iterator<String> it = this.viewModel.averagesList().iterator();
    while (it.hasNext()) {
      String avgHeader = it.next();
      if (!totalsList.contains(avgHeader)) {
        avgNotAvailable = avgHeader;
        break;
      }
    }

    assertTrue(avgNotAvailable.isEmpty(), "Average header not shown: " + avgNotAvailable);
  }

  @Test
  void demographicsList() {
    Demographic[] demographics = Demographics.Demographic.values();
    List<Demographic> demographicsList = Arrays.asList(demographics);
    String demNotAvailable = "";

    Iterator<Demographic> it = this.viewModel.demographicsList().iterator();
    while (it.hasNext()) {
      Demographic demHeader = it.next();
      if (!demographicsList.contains(demHeader)) {
        demNotAvailable = demHeader.toString();
        break;
      }
    }

    assertTrue(demNotAvailable.isEmpty(), "Demographic header not shown: " + demNotAvailable);
  }

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
}