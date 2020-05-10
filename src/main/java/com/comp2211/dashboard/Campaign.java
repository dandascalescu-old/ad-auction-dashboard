package com.comp2211.dashboard;

import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.model.data.Filter;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.io.DatabaseManager.Cost;

import de.saxsys.mvvmfx.MvvmFX;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Campaign object used to represent each individual campaign. Contains impression, click and server
 * info.
 */
public class Campaign {

  // Used as a singleton across app
  private static List<Campaign> allCampaigns = new ArrayList<>();

  private String campaignName;
  private int campaignID;
  private DatabaseManager dbManager;

  private Filter appliedFilter;

  private BigDecimal totalClickCost, totalImpressionCost, averageAcquisitionCost;
  private long clickDataCount, impressionDataCount, serverDataCount, uniquesCount, bouncesCount, conversionsCount;

  private HashMap<String, BigDecimal> cachedDatedAcquisitionCostAverages, cachedDatedImpressionCostAverages, cachedDatedClickCostAverages;
  private HashMap<String, Long> cachedDatedImpressionTotals, cachedDatedClickTotals, cachedDatedUniqueTotals, cachedDatedBounceTotals, cachedDatedAcquisitionTotals;
  private HashMap<String, BigDecimal> cachedAgePercentage, cachedGenderPercentage, cachedIncomePercentage, cachedContextPercentage;

  public static List<Campaign> getCampaigns(){
    return allCampaigns;
  }
  public static void removeAllCampaigns() { allCampaigns = new ArrayList<>(); }

  public static Campaign getCampaignByID(int id){
    for(Campaign c : allCampaigns) {
      if(c.getCampaignID() == id) {
        return c;
      }
    }
    return null;
  }

  /**
   * Constructor for a campaign.
   *
   * @param campaignName The unique identifier for a given campaign. Must be given for each campaign.
   */
  public Campaign(int campaignID, String campaignName, DatabaseManager dbManager) {
    this.campaignID = campaignID;
    this.campaignName = campaignName;
    this.dbManager = Objects.requireNonNull(dbManager, "dbManager must not be null");

    cachedDatedAcquisitionCostAverages = new LinkedHashMap<>();
    cachedDatedImpressionCostAverages = new LinkedHashMap<>();
    cachedDatedClickCostAverages = new LinkedHashMap<>();

    cachedDatedImpressionTotals = new LinkedHashMap<>();
    cachedDatedClickTotals = new LinkedHashMap<>();
    cachedDatedUniqueTotals = new LinkedHashMap<>();
    cachedDatedBounceTotals = new LinkedHashMap<>();
    cachedDatedAcquisitionTotals = new LinkedHashMap<>();

    cachedAgePercentage = new LinkedHashMap<>();
    cachedGenderPercentage = new LinkedHashMap<>();
    cachedIncomePercentage = new LinkedHashMap<>();
    cachedContextPercentage = new LinkedHashMap<>();

    allCampaigns.add(this);
  }

  @Override
  public String toString(){
    return getCampaignName();
  }

  public int getCampaignID() {
    return campaignID;
  }

  /**
   * Get the name of the campaign
   * @return the name
   */
  public String getCampaignName() {
    return campaignName;
  }

  public boolean hasAppliedFilter() {
    return appliedFilter != null;
  }
  public Filter getAppliedFilter() {
    return appliedFilter;
  }

  /**
   * Fetches and caches entries from the database
   */
  public void cacheData(Filter filter) {
    if (appliedFilter != null && filter.isEqualTo(appliedFilter)) {
      Logger.log("Data not cached - filter provided was identical to current applied filter.");
      return;
    }

    //TODO maybe cache IDs for certain demographics?
    System.out.println("dbManager: " + dbManager);
    clickDataCount = dbManager.retrieveDataCount(DatabaseManager.Table.click_table, filter);
    impressionDataCount = dbManager.retrieveDataCount(DatabaseManager.Table.impression_table, filter);
    serverDataCount = dbManager.retrieveDataCount(DatabaseManager.Table.server_table, filter);
    uniquesCount = dbManager.retrieveDataCount(DatabaseManager.Table.click_table, true, filter);
    conversionsCount = dbManager.retrieveAcquisitionCount(filter);

    totalClickCost = dbManager.retrieveTotalCost(Cost.Click_Cost, filter);
    totalImpressionCost = dbManager.retrieveTotalCost(Cost.Impression_Cost, filter);
    averageAcquisitionCost = dbManager.retrieveAverageAcquisitionCost(filter);

    clearCache();

    cachedDatedClickCostAverages.putAll(dbManager.retrieveDatedAverageCost(Cost.Click_Cost, filter));
    cachedDatedImpressionCostAverages.putAll(dbManager.retrieveDatedAverageCost(Cost.Impression_Cost, filter));
    cachedDatedAcquisitionCostAverages.putAll(dbManager.retrieveDatedAverageAcquisitionCost(filter));

    cachedDatedImpressionTotals.putAll(dbManager.retrieveDatedImpressionTotals(filter));
    cachedDatedClickTotals.putAll(dbManager.retrieveDatedClickTotals(filter));
    cachedDatedUniqueTotals.putAll(dbManager.retrieveDatedUniqueTotals(filter));
    cachedDatedAcquisitionTotals.putAll(dbManager.retrieveDatedAcquisitionTotals(filter));

    cachedAgePercentage.putAll(percentageMap(Demographic.Age, dbManager.retrieveDemographics(Demographic.Age, filter)));
    cachedGenderPercentage.putAll(percentageMap(Demographic.Gender, dbManager.retrieveDemographics(Demographic.Gender, filter)));
    cachedIncomePercentage.putAll(percentageMap(Demographic.Income, dbManager.retrieveDemographics(Demographic.Income, filter)));
    cachedContextPercentage.putAll(percentageMap(Demographic.Context, dbManager.retrieveDemographics(Demographic.Context, filter)));

    appliedFilter = filter;

    Logger.log("Data cached successfully.");
  }

  public void clearCache() {
    cachedDatedClickCostAverages.clear();
    cachedDatedImpressionCostAverages.clear();
    cachedDatedAcquisitionCostAverages.clear();

    cachedDatedImpressionTotals.clear();
    cachedDatedClickTotals.clear();
    cachedDatedUniqueTotals.clear();
    cachedDatedBounceTotals.clear();
    cachedDatedAcquisitionTotals.clear();

    cachedAgePercentage.clear();
    cachedGenderPercentage.clear();
    cachedIncomePercentage.clear();
    cachedContextPercentage.clear();
  }

  public long getClickDataCount() {
    return clickDataCount;
  }

  public long getImpressionDataCount() {
    return impressionDataCount;
  }

  public long getServerDataCount() {
    return serverDataCount;
  }

  public long getUniquesCount() {
    return uniquesCount;
  }

  public long getBouncesCount() {
    return bouncesCount;
  }

  public long getConversionsCount() {
    return conversionsCount;
  }

  /**
   * Sums up all the costs of each click.
   *
   * @return Total cost of all click in campaign.
   */
  public BigDecimal getTotalClickCost() {
    return totalClickCost;
  }

  /**
   * Sums up all the costs of each impression.
   *
   * @return Total cost of all impressions in campaign.
   */
  public BigDecimal getTotalImpressionCost() {
    return totalImpressionCost;
  }

  //TODO use these in the dashboard
  /**
   * Calculates the average cost per click using the total click cost divided by the number of
   * clicks.
   *
   * @return Average cost per click given in pence.
   */
  public BigDecimal getAvgCostPerClick() {
    if (clickDataCount == 0) {
      return BigDecimal.ZERO;
    }
    return getTotalCost().divide(BigDecimal.valueOf(clickDataCount), 6, RoundingMode.HALF_UP);
  }

  /**
   * Calculates the click through rate in percentage using click and impression lists.
   *
   * @return Click through rate as a percentage.
   */
  public BigDecimal getClickThroughRate() {
    if (impressionDataCount == 0) {
      return BigDecimal.ZERO;
    }
    return BigDecimal.valueOf(clickDataCount).divide(BigDecimal.valueOf(impressionDataCount), 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
  }

  public void updateBouncesByTime(long maxSeconds, boolean allowInf, Filter filter) {
    if (maxSeconds < 0) {
      Logger.log("Attempted bounce calculation with negative value");
      return;
    }
    /*if (serverDataCount == 0) {
      return;
    }*/
    bouncesCount = dbManager.retrieveBouncesCountByTime(maxSeconds, allowInf, filter);
    cachedDatedBounceTotals.clear();
    cachedDatedBounceTotals.putAll(dbManager.retrieveDatedBounceTotalsByTime(maxSeconds, allowInf, filter));
    MvvmFX.getNotificationCenter().publish("Bounce");
  }

  public void updateBouncesByPages(byte maxPages, Filter filter) {
    if (maxPages < 0) {
      Logger.log("Attempted bounce calculation with negative value, returning 0");
      return;
    }
    /*if (serverDataCount == 0) {
      bouncesCount = 0L;
      cachedDatedBounceTotals.clear();
      return;
    }*/
    bouncesCount = dbManager.retrieveBouncesCountByPages(maxPages, filter);
    cachedDatedBounceTotals.clear();
    cachedDatedBounceTotals.putAll(dbManager.retrieveDatedBounceTotalsByPages(maxPages, filter));

    MvvmFX.getNotificationCenter().publish("Bounce");
  }

  /**
   * Calculates the bounce rate as the percentage of server entries that result in a bounce
   * @return Bounce rate as a percentage
   */
  public BigDecimal getBounceRate() {
    if (serverDataCount == 0) {
      return BigDecimal.ZERO;
    }
    return BigDecimal.valueOf(bouncesCount).divide(BigDecimal.valueOf(serverDataCount), 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
  }

  public BigDecimal getBouncesPerConversion() {
    if (conversionsCount == 0) {
      return BigDecimal.ZERO;
    }
    return BigDecimal.valueOf(bouncesCount).divide(BigDecimal.valueOf(conversionsCount), 6, RoundingMode.HALF_UP);
  }

  public BigDecimal getConversionsPerUnique() {
    if (uniquesCount == 0) {
      return BigDecimal.ZERO;
    }
    return BigDecimal.valueOf(conversionsCount).divide(BigDecimal.valueOf(uniquesCount), 6, RoundingMode.HALF_UP);
  }

  /**
   * Calculates the total cost of the campaign using the sum of the costs of impression and clicks.
   *
   * @return Total cost of a campaign in pence.
   */
  public BigDecimal getTotalCost() {
    return getTotalClickCost().add(getTotalImpressionCost());
  }

  /**
   * Calculates the average cost per acquisition/conversion by summing all converted clicks and
   * dividing by the count.
   *
   * @return The average cost per acquisition given in pence.
   */
  public BigDecimal getAvgCostPerAcquisition() {
    return averageAcquisitionCost;
  }

  /**
   * Calculates/estimates the cost per thousand impression by calculating the average cost of a
   * single impression multiplied by 1000
   *
   * @return The average cost per acquisition given in pence.
   */
  public BigDecimal getCostPerThousandImpressions() {
    if (impressionDataCount == 0) {
      return BigDecimal.ZERO;
    }
    return getTotalImpressionCost().divide(BigDecimal.valueOf(impressionDataCount), 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(1000));
  }

  /**
   * Calculates the average acquisition cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public HashMap<String, BigDecimal> getDatedAcquisitionCostAverages() {
    return cachedDatedAcquisitionCostAverages;
  }

  /**
   * Calculates the average impression cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public HashMap<String, BigDecimal> getDatedImpressionCostAverages() {
    return cachedDatedImpressionCostAverages;
  }

  /**
   * Calculates the average click cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public HashMap<String, BigDecimal> getDatedClickCostAverages() {
    return cachedDatedClickCostAverages;
  }

  /**
   * Calculates the total impressions for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the total for
   *     that date as the value.
   */
  public HashMap<String, Long> getDatedImpressionTotals() {
    return cachedDatedImpressionTotals;
  }

  /**
   * Calculates the total clicks for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the total for
   *     that date as the value.
   */
  public HashMap<String, Long> getDatedClickTotals() {
    return cachedDatedClickTotals;
  }

  /**
   * Calculates the total uniques for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the total for
   *     that date as the value.
   */
  public HashMap<String, Long> getDatedUniqueTotals() {
    return cachedDatedUniqueTotals;
  }

  /**
   * Calculates the total bounces for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the total for
   *     that date as the value.
   */
  public HashMap<String, Long> getDatedBounceTotals() {
    return cachedDatedBounceTotals;
  }

  /**
   * Calculates the total conversions for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the total for
   *     that date as the value.
   */
  public HashMap<String, Long> getDatedAcquisitionTotals() {
    return cachedDatedAcquisitionTotals;
  }

  /**
   * Calculates the percentage of each group for a given Demographic type
   * @param type the Demographic type to use
   * @return HashMap containing the groups as keys, and the percentage of each group in the campaign.
   */
  public HashMap<String, BigDecimal> getPercentageMap(Demographic type) {
    switch(type) {
      case Age:
        return getAgePercentage();
      case Context:
        return getContextPercentage();
      case Gender:
        return getGenderPercentage();
      case Income:
        return getIncomePercentage();
      default:
        return null;
    }
  }

  /**
   * Calculates percentage of each age group.
   * @return HashMap containing the age groups as keys, and the percentage of each group in the campaign.
   */
  private HashMap<String, BigDecimal> getAgePercentage() {
    return cachedAgePercentage;
  }

  /**
   * Calculates percentage of each gender.
   * @return HashMap containing the gender groups as keys, and the percentage of each gender in the campaign.
   */
  private HashMap<String, BigDecimal> getGenderPercentage() {
    return cachedGenderPercentage;
  }

  /**
   * Calculates percentage of each income group.
   * @return HashMap containing the income groups as keys, and the percentage of each group in the campaign.
   */
  private HashMap<String, BigDecimal> getIncomePercentage() {
    return cachedIncomePercentage;
  }

  /**
   * Calculates percentage of each context.
   * @return HashMap containing the context types as keys, and the percentage of each context in the campaign.
   */
  private HashMap<String, BigDecimal> getContextPercentage() {
    return cachedContextPercentage;
  }

  private HashMap<String, BigDecimal> percentageMap (Demographic demographic, HashMap<String, Long> dataMap) {
    HashMap<String, BigDecimal> resultMap = new LinkedHashMap<>();
    for (Entry<String, Long> entry : dataMap.entrySet()) {
      Long count = entry.getValue();
      if(count == null || count == 0 || impressionDataCount == 0) {
        resultMap.put(entry.getKey(), BigDecimal.ZERO);
      } else {
        resultMap.put(entry.getKey(), BigDecimal.valueOf(count).divide(BigDecimal.valueOf(impressionDataCount), 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
      }
    }
    return resultMap;
  }
}
