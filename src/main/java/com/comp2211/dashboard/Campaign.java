package com.comp2211.dashboard;

import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.io.DatabaseManager.Cost;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Campaign object used to represent each individual campaign. Contains impression, click and server
 * info.
 */
public class Campaign {
  private static List<Campaign> allCampaigns = new ArrayList<Campaign>();

  private String campaignID;
  private DatabaseManager dbManager;

  private BigDecimal totalClickCost, totalImpressionCost, averageAcquisitionCost;
  private long clickDataCount, impressionDataCount, serverDataCount, uniquesCount;

  private HashMap<String, BigDecimal> cachedDatedAcquisitionCostAverages, cachedDatedImpressionCostAverages, cachedDatedClickCostAverages;
  private HashMap<String, BigDecimal> cachedAgePercentage, cachedGenderPercentage, cachedIncomePercentage, cachedContextPercentage;

  public static List<Campaign> getCampaigns(){
    return allCampaigns;
  }

  public static Campaign getCampaignByID(String id){
    for(Campaign c : allCampaigns) {
      if(c.getCampaignID().equals(id)) {
        return c;
      }
    }
    return null;
  }

  /**
   * Constructor for a campaign.
   *
   * @param campaignID The unique identifier for a given campaign. Must be given for each campaign.
   */
  public Campaign(String campaignID, DatabaseManager dbManager) {
    this.campaignID = campaignID;
    this.dbManager = dbManager;

    cachedDatedAcquisitionCostAverages = new LinkedHashMap<String, BigDecimal>();
    cachedDatedImpressionCostAverages = new LinkedHashMap<String, BigDecimal>();
    cachedDatedClickCostAverages = new LinkedHashMap<String, BigDecimal>();
    cachedAgePercentage = new LinkedHashMap<String, BigDecimal>();
    cachedGenderPercentage = new LinkedHashMap<String, BigDecimal>();
    cachedIncomePercentage = new LinkedHashMap<String, BigDecimal>();
    cachedContextPercentage = new LinkedHashMap<String, BigDecimal>();

    allCampaigns.add(this);
  }

  @Override
  public String toString(){
    return getCampaignID();
  }

  /**
   * Get the id of the campaign
   * @return the id
   */
  public String getCampaignID() {
    return campaignID;
  }

  /**
   * Fetches and caches entries from the database
   */
  public void cacheData() {
    clickDataCount = dbManager.retrieveDataCount(dbManager.getClickTable());
    impressionDataCount = dbManager.retrieveDataCount(dbManager.getImpressionTable());
    serverDataCount = dbManager.retrieveDataCount(dbManager.getServerTable());
    uniquesCount = dbManager.retrieveDataCount(dbManager.getClickTable(), true);

    totalClickCost = dbManager.retrieveTotalCost(Cost.Click_Cost);
    totalImpressionCost = dbManager.retrieveTotalCost(Cost.Impression_Cost);
    averageAcquisitionCost = dbManager.retrieveAverageAcquisitionCost();

    Logger.log("Data cached successfully.");
  }

  public void clearCache() {
    cachedDatedClickCostAverages.clear();
    cachedDatedImpressionCostAverages.clear();
    cachedDatedAcquisitionCostAverages.clear();
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
    if (cachedDatedAcquisitionCostAverages != null && !cachedDatedAcquisitionCostAverages.isEmpty()) {
      return cachedDatedAcquisitionCostAverages;
    }

    cachedDatedAcquisitionCostAverages.clear();
    cachedDatedAcquisitionCostAverages.putAll(dbManager.retrieveDatedAverageAcquisitionCost());
    return cachedDatedAcquisitionCostAverages;
  }

  /**
   * Calculates the average impression cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public HashMap<String, BigDecimal> getDatedImpressionCostAverages() {
    if (cachedDatedImpressionCostAverages != null && !cachedDatedImpressionCostAverages.isEmpty()) {
      return cachedDatedImpressionCostAverages;
    }

    cachedDatedImpressionCostAverages.clear();
    cachedDatedImpressionCostAverages.putAll(dbManager.retrieveDatedAverageCost(Cost.Impression_Cost));
    return cachedDatedImpressionCostAverages;
  }

  /**
   * Calculates the average click cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public HashMap<String, BigDecimal> getDatedClickCostAverages() {
    if (cachedDatedClickCostAverages != null && !cachedDatedClickCostAverages.isEmpty()) {
      return cachedDatedClickCostAverages;
    }

    cachedDatedClickCostAverages.clear();
    cachedDatedClickCostAverages.putAll(dbManager.retrieveDatedAverageCost(Cost.Click_Cost));
    return cachedDatedClickCostAverages;
  }

  //todo: Consider making a generic type for values that can return percentages as opposed to just demographics
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
    if (cachedAgePercentage != null && !cachedAgePercentage.isEmpty()) {
      return cachedAgePercentage;
    }

    cachedAgePercentage.clear();
    cachedAgePercentage.putAll(percentageMap(Demographic.Age, dbManager.retrieveDemographics(Demographic.Age)));
    return cachedAgePercentage;
  }

  /**
   * Calculates percentage of each gender.
   * @return HashMap containing the gender groups as keys, and the percentage of each gender in the campaign.
   */
  private HashMap<String, BigDecimal> getGenderPercentage() {
    if (cachedGenderPercentage != null && !cachedGenderPercentage.isEmpty()) {
      return cachedGenderPercentage;
    }

    cachedGenderPercentage.clear();
    cachedGenderPercentage.putAll(percentageMap(Demographic.Gender, dbManager.retrieveDemographics(Demographic.Gender)));
    return cachedGenderPercentage;
  }

  /**
   * Calculates percentage of each income group.
   * @return HashMap containing the income groups as keys, and the percentage of each group in the campaign.
   */
  private HashMap<String, BigDecimal> getIncomePercentage() {
    if (cachedIncomePercentage != null && !cachedIncomePercentage.isEmpty()) {
      return cachedIncomePercentage;
    }

    cachedIncomePercentage.clear();
    cachedIncomePercentage.putAll(percentageMap(Demographic.Income, dbManager.retrieveDemographics(Demographic.Income)));
    return cachedIncomePercentage;
  }

  /**
   * Calculates percentage of each context.
   * @return HashMap containing the context types as keys, and the percentage of each context in the campaign.
   */
  private HashMap<String, BigDecimal> getContextPercentage() {
    if (cachedContextPercentage != null && !cachedContextPercentage.isEmpty()) {
      return cachedContextPercentage;
    }

    cachedContextPercentage.clear();
    cachedContextPercentage.putAll(percentageMap(Demographic.Context, dbManager.retrieveDemographics(Demographic.Context)));
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

  //todo: may benefit from using enum instead of string

  /**
   * Used for
   * @param datatype
   * @return
   */
  public HashMap<String, BigDecimal> getLineChartData(String datatype){
    switch(datatype){
      case "Average Cost of Click":
        return getDatedClickCostAverages();
      case "Average Cost of Impression":
        return getDatedImpressionCostAverages();
      case "Average Cost of Acquisition":
        return getDatedAcquisitionCostAverages();
      default:
        return null;
    }
  }
}
