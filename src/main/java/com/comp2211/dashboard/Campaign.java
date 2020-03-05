package com.comp2211.dashboard;

import com.comp2211.dashboard.model.data.*;
import com.comp2211.dashboard.io.DatabaseManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Campaign object used to represent each individual campaign. Contains impression, click and server
 * info.
 */
public class Campaign {
  private String campaignID;
  private ArrayList<ClickData> clickDataList;
  private ArrayList<ImpressionData> impressionDataList;
  private ArrayList<ServerData> serverDataList;

  private BigDecimal totalClickCost = BigDecimal.ZERO;
  private BigDecimal totalImpressionCost = BigDecimal.ZERO;
  private long clickDataCount, impressionDataCount, serverDataCount = 0L;

  private boolean initialLoad = false;

  private LinkedHashMap<String, BigDecimal> cachedDatedAcquisitionCostAverages, cachedDatedImpressionCostAverages, cachedDatedClickCostAverages;
  private LinkedHashMap<String, BigDecimal> cachedAgePercentage, cachedGenderPercentage, cachedIncomePercentage, cachedContextPercentage;

  /**
   * Constructor for a campaign.
   *
   * @param campaignID The unique identifier for a given campaign. Must be given for each campaign.
   */
  public Campaign(String campaignID) {
    this.campaignID = campaignID;
    clickDataList = new ArrayList<ClickData>();
    impressionDataList = new ArrayList<ImpressionData>();
    serverDataList = new ArrayList<ServerData>();

    cachedDatedAcquisitionCostAverages = new LinkedHashMap<String, BigDecimal>();
    cachedDatedImpressionCostAverages = new LinkedHashMap<String, BigDecimal>();
    cachedDatedClickCostAverages = new LinkedHashMap<String, BigDecimal>();
    cachedAgePercentage = new LinkedHashMap<String, BigDecimal>();
    cachedGenderPercentage = new LinkedHashMap<String, BigDecimal>();
    cachedIncomePercentage = new LinkedHashMap<String, BigDecimal>();
    cachedContextPercentage = new LinkedHashMap<String, BigDecimal>();
  }

  /**
   * Get the id of the campaign
   * @return the id
   */
  public String getCampaignID() {
    return campaignID;
  }

  /**
   * Check if all clickData are loaded from the database
   * @return true if loaded
   */
  public boolean isClickDataLoaded() {
    if (initialLoad) {
      return clickDataList.size() == clickDataCount && clickDataCount != 0;
    }
    return false;
  }

  /**
   * Check if all impressionData are loaded from the database
   * @return true if loaded
   */
  public boolean isImpressionDataLoaded() {
    if (initialLoad) {
      return impressionDataList.size() == impressionDataCount && impressionDataCount != 0;
    }
    return false;
  }

  /**
   * Check if all serverData are loaded from the database
   * @return true if loaded
   */
  public boolean isServerDataLoaded() {
    if (initialLoad) {
      return serverDataList.size() == serverDataCount && serverDataCount != 0;
    }
    return false;
  }

  /**
   * Fetches and caches entries from the database
   * @param limit the amount of entries to fetch, use 0 to process all entries.
   */
  public void cacheData(int limit) {
    clickDataCount = DatabaseManager.retrieveDataCount(DatabaseManager.Table.click_table);
    impressionDataCount = DatabaseManager.retrieveDataCount(DatabaseManager.Table.impression_table);
    serverDataCount = DatabaseManager.retrieveDataCount(DatabaseManager.Table.server_table);

    initialLoad = true;

    totalClickCost = DatabaseManager.retrieveTotalClickCost();
    totalImpressionCost = DatabaseManager.retrieveTotalImpressionCost();

    setClickDataList(DatabaseManager.retrieveClickData(limit));
    setImpressionDataList(DatabaseManager.retrieveImpressionData(limit));
    setServerDataList(DatabaseManager.retrieveServerData(limit));

    clearCache();

    System.out.println("Data loaded successfully.");
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

  /**
   * Returns impression data list for the campaign.
   *
   * @return Impression data list from the campaign. Each impression contains the date/time, gender,
   *     age, income, context and cost.
   */
  public ArrayList<ImpressionData> getImpressionDataList() {
    return impressionDataList;
  }

  /**
   * Sets the impression data list.
   *
   * @param impressionDataList Premade impression data list to set.
   */
  public void setImpressionDataList(ArrayList<ImpressionData> impressionDataList) {
    this.impressionDataList.clear();
    this.impressionDataList.addAll(impressionDataList);
  }

  /**
   * Returns server data list for the campaign.
   *
   * @return Server data list from the campaign. Each server data contains advert entry date/time,
   *     exit date/time, pages viewed, conversion.
   */
  public ArrayList<ServerData> getServerDataList() {
    return serverDataList;
  }

  /**
   * Sets the impression data list.
   *
   * @param serverDataList Premade server data list to set.
   */
  public void setServerDataList(ArrayList<ServerData> serverDataList) {
    this.serverDataList.clear();
    this.serverDataList.addAll(serverDataList);
  }

  /**
   * Returns click data list for the campaign.
   *
   * @return Click data list from the campaign. Each click contains date/time and cost.
   */
  public ArrayList<ClickData> getClickDataList() {
    return clickDataList;
  }

  /**
   * Sets the impression data list.
   *
   * @param clickDataList Premade click data list to set.
   */
  public void setClickDataList(ArrayList<ClickData> clickDataList) {
    this.clickDataList.clear();
    this.clickDataList.addAll(clickDataList);
  }

  /**
   * Retrieves click data from given ID.
   *
   * @return Click data with matched ID. If ID not found, will return an empty ArrayList.
   */
  public ArrayList<ClickData> getClickDataByID(String id) {
    ArrayList<ClickData> dataWithID = new ArrayList<ClickData>();
    for (ClickData data : getClickDataList()) {
      if (data.getId().equals(id)) {
        dataWithID.add(data);
      }
    }
    return dataWithID;
  }

  /**
   * Retrieves impression data from given ID.
   *
   * @return Impression data with matched ID. If ID not found, will return an empty ArrayList.
   */
  public ArrayList<ImpressionData> getImpressionDataByID(String id) {
    ArrayList<ImpressionData> dataWithID = new ArrayList<ImpressionData>();
    for (ImpressionData data : getImpressionDataList()) {
      if (data.getId().equals(id)) {
        dataWithID.add(data);
      }
    }
    return dataWithID;
  }

  /**
   * Retrieves server data from given ID.
   *
   * @return Server data with matched ID. If ID not found, will return an empty ArrayList.
   */
  public ArrayList<ServerData> getServerDataByID(String id) {
    ArrayList<ServerData> dataWithID = new ArrayList<ServerData>();
    for (ServerData data : getServerDataList()) {
      if (data.getId().equals(id)) {
        dataWithID.add(data);
      }
    }
    return dataWithID;
  }

  /**
   * Adds given click data to campaign.
   *
   * @param clickData Click data object to add to campaign.
   */
  public void addClickData(ClickData clickData) {
    clickDataList.add(clickData);
  }

  /**
   * Removes given click data from campaign.
   *
   * @param clickData Click data object to remove form the list.
   */
  public void removeClickDataByID(ClickData clickData) {
    clickDataList.remove(clickData);
  }

  /**
   * Adds given impression data to campaign.
   *
   * @param impressionData Impression data object to add to campaign.
   */
  public void addImpressionData(ImpressionData impressionData) {
    impressionDataList.add(impressionData);
  }

  /**
   * Removes given impression data from campaign.
   *
   * @param impressionData Impression data object to remove form the list.
   */
  public void removeImpressionDataByID(ImpressionData impressionData) {
    impressionDataList.remove(impressionData);
  }

  /**
   * Adds given server data to campaign.
   *
   * @param serverData Server data object to add to campaign.
   */
  public void addServerData(ServerData serverData) {
    serverDataList.add(serverData);
  }

  /**
   * Removes given server data from campaign.
   *
   * @param serverData Server data object to remove form the list.
   */
  public void removeServerDataByID(ServerData serverData) {
    serverDataList.remove(serverData);
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
    return BigDecimal.valueOf(clickDataCount)
        .divide(BigDecimal.valueOf(impressionDataCount), 6, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(100));
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
    ArrayList<String> checked = new ArrayList<String>();
    BigDecimal sum = BigDecimal.ZERO;
    long count = 0L;
    for (ClickData cd : getClickDataList()) {
      if(checked.contains(cd.getId())) {
        continue;
      }
      checked.add(cd.getId());
      for (ServerData sd : getServerDataByID(cd.getId())) {
        if (sd.hasConverted()) {
          sum.add(cd.getClickCost());
          count++;
        }
      }
    }
    if (count == 0) {
      return BigDecimal.ZERO;
    }
    return sum.divide(BigDecimal.valueOf(count), 6, RoundingMode.HALF_UP);
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
  public LinkedHashMap<String, BigDecimal> getDatedAcquisitionCostAverages() {
    if (cachedDatedAcquisitionCostAverages != null && !cachedDatedAcquisitionCostAverages.isEmpty()) {
      return cachedDatedAcquisitionCostAverages;
    }

    cachedDatedAcquisitionCostAverages.clear();
    cachedDatedAcquisitionCostAverages.putAll(DatabaseManager.retrieveAcquisitionCostPerDate());
    return cachedDatedAcquisitionCostAverages;
  }

  /**
   * Calculates the average impression cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public LinkedHashMap<String, BigDecimal> getDatedImpressionCostAverages() {
    if (cachedDatedImpressionCostAverages != null && !cachedDatedImpressionCostAverages.isEmpty()) {
      return cachedDatedImpressionCostAverages;
    }

    cachedDatedImpressionCostAverages.clear();
    cachedDatedImpressionCostAverages.putAll(DatabaseManager.retrieveAverageImpressionCostPerDate());
    return cachedDatedImpressionCostAverages;
  }

  /**
   * Calculates the average click cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public LinkedHashMap<String, BigDecimal> getDatedClickCostAverages() {
    if (cachedDatedClickCostAverages != null && !cachedDatedClickCostAverages.isEmpty()) {
      return cachedDatedClickCostAverages;
    }

    cachedDatedClickCostAverages.clear();
    cachedDatedClickCostAverages.putAll(DatabaseManager.retrieveAverageClickCostPerDate());
    return cachedDatedClickCostAverages;
  }

  /**
   * Calculates percentage of each age group.
   *
   * @return LinkedHashMap containing the age groups as keys, and the percentage of each group in the
   *     campaign.
   */
  public LinkedHashMap<String, BigDecimal> getAgePercentage() {
    if (cachedAgePercentage != null && !cachedAgePercentage.isEmpty()) {
      return cachedAgePercentage;
    }

    LinkedHashMap<String, SumWithCount> ageMap = new LinkedHashMap<>();

    ageMap.put("<25", new SumWithCount());
    ageMap.put("25-34", new SumWithCount());
    ageMap.put("35-44", new SumWithCount());
    ageMap.put("45-54", new SumWithCount());
    ageMap.put(">54", new SumWithCount());

    for (ImpressionData impressionData : impressionDataList) {
      ageMap.get(impressionData.getAge()).incrementCount();
    }

    cachedAgePercentage.clear();
    cachedAgePercentage.putAll(percentageMap(ageMap));
    return cachedAgePercentage;
  }

  /**
   * Calculates percentage of each gender.
   *
   * @return LinkedHashMap containing the gender groups as keys, and the percentage of each gender in the
   *     campaign.
   */
  public LinkedHashMap<String, BigDecimal> getGenderPercentage() {
    if (cachedGenderPercentage != null && !cachedGenderPercentage.isEmpty()) {
      return cachedGenderPercentage;
    }

    LinkedHashMap<String, SumWithCount> genderMap = new LinkedHashMap<>();

    genderMap.put("Male", new SumWithCount());
    genderMap.put("Female", new SumWithCount());

    for (ImpressionData impressionData : impressionDataList) {
      String gender = impressionData.getGender() ? "Male" : "Female";
      genderMap.get(gender).incrementCount();
    }

    cachedGenderPercentage.clear();
    cachedGenderPercentage.putAll(percentageMap(genderMap));
    return cachedGenderPercentage;
  }

  /**
   * Calculates percentage of each income group. Low=0,medium=1,high=2
   *
   * @return LinkedHashMap containing the income groups as keys, and the percentage of each group in
   *     the campaign.
   */
  public LinkedHashMap<String, BigDecimal> getIncomePercentage() {
    if (cachedIncomePercentage != null && !cachedIncomePercentage.isEmpty()) {
      return cachedIncomePercentage;
    }

    LinkedHashMap<String, SumWithCount> incomeMap = new LinkedHashMap<>();

    incomeMap.put("Low", new SumWithCount());
    incomeMap.put("Medium", new SumWithCount());
    incomeMap.put("High", new SumWithCount());

    for (ImpressionData impressionData : impressionDataList) {
      switch (impressionData.getIncome()) {
        case 0:
          incomeMap.get("Low").incrementCount();
          break;
        case 1:
          incomeMap.get("Medium").incrementCount();
          break;
        case 2:
          incomeMap.get("High").incrementCount();
          break;  
      }
    }

    cachedIncomePercentage.clear();
    cachedIncomePercentage.putAll(percentageMap(incomeMap));
    return cachedIncomePercentage;
  }

  /**
   * Calculates percentage of each context. Blog=0,News=1,Shopping=2,Social Media=3.
   *
   * @return LinkedHashMap containing the context types as keys, and the percentage of each context in the
   *     campaign.
   */
  public LinkedHashMap<String, BigDecimal> getContextPercentage() {
    if (cachedContextPercentage != null && !cachedContextPercentage.isEmpty()) {
      return cachedContextPercentage;
    }

    LinkedHashMap<String, SumWithCount> contextMap = new LinkedHashMap<>();

    contextMap.put("Blog", new SumWithCount());
    contextMap.put("News", new SumWithCount());
    contextMap.put("Shopping", new SumWithCount());
    contextMap.put("Social Media", new SumWithCount());

    for (ImpressionData impressionData : impressionDataList) {
      switch (impressionData.getContext()) {
        case 0:
          contextMap.get("Blog").incrementCount();
          break;
        case 1:
          contextMap.get("News").incrementCount();
          break;
        case 2:
          contextMap.get("Shopping").incrementCount();
          break;
        case 3:
          contextMap.get("Social Media").incrementCount();
          break;
      }
    }

    cachedContextPercentage.clear();
    cachedContextPercentage.putAll(percentageMap(contextMap));
    return cachedContextPercentage;
  }

  private LinkedHashMap<String, BigDecimal> percentageMap (LinkedHashMap<String, SumWithCount> dataMap) {
    LinkedHashMap<String, BigDecimal> resultMap = new LinkedHashMap<>();

    for (String key : dataMap.keySet()) {
      long count = dataMap.get(key).getCount();
      if(count == 0 || impressionDataCount == 0) {
        resultMap.put(key, BigDecimal.ZERO);        
      } else {
        resultMap.put(key, BigDecimal.valueOf(count).divide(BigDecimal.valueOf(impressionDataCount), 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
      }
    }
    return resultMap;
  }
}
