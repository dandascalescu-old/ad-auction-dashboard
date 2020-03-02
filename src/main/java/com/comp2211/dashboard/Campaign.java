package com.comp2211.dashboard;

import com.comp2211.dashboard.data.*;
import com.comp2211.dashboard.io.DatabaseManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Campaign object used to represent each individual campaign. Contains impression, click and server
 * info.
 */
public class Campaign {
  private String campaignID;
  private ArrayList<ImpressionData> impressionDataList;
  private ArrayList<ServerData> serverDataList;
  private ArrayList<ClickData> clickDataList;

  private BigDecimal totalClickCost = BigDecimal.ZERO;
  private BigDecimal totalImpressionCost = BigDecimal.ZERO;
  private long clickDataCount, impressionDataCount, serverDataCount = 0L;

  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  private boolean initialLoad = false;

  /**
   * Constructor for a campaign.
   *
   * @param campaignID The unique identifier for a given campaign. Must be given for each campaign.
   */
  public Campaign(String campaignID) {
    this.campaignID = campaignID;
  }

  public boolean isClickDataLoaded() {
	if (initialLoad) {
      return clickDataList.size() == clickDataCount;
	}
	return false;
  }

  public boolean isImpressionDataLoaded() {
	if (initialLoad) {
      return impressionDataList.size() == impressionDataCount;
	}
	return false;
  }

  public boolean isServerDataLoaded() {
	if (initialLoad) {
      return serverDataList.size() == serverDataCount;
    }
    return false;
  }

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
    this.impressionDataList = impressionDataList;
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
    this.serverDataList = serverDataList;
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
    this.clickDataList = clickDataList;
  }

  /**
   * Retrieves click data from given ID.
   *
   * @return Click data with matched ID. If ID not found, will return null.
   */
  public ClickData getClickDataByID(String id) {
    return (ClickData) getDataByID(getServerDataList(), id);
  }

  /**
   * Retrieves impression data from given ID.
   *
   * @return Impression data with matched ID. If ID not found, will return null.
   */
  public ImpressionData getImpressionDataByID(String id) {
    return (ImpressionData) getDataByID(getServerDataList(), id);
  }

  /**
   * Retrieves server data from given ID.
   *
   * @return Server data with matched ID. If ID not found, will return null.
   */
  public ServerData getServerDataByID(String id) {
    return (ServerData) getDataByID(getServerDataList(), id);
  }

  /**
   * Generic method for grabbing campaign data from given ID.
   *
   * @param dataList The data list to scan/query. Must be a subclass of Campaign Data.
   * @param id The ID to find.
   * @return Campaign Data with matched ID. If ID not found, will return null.
   */
  private CampaignData getDataByID(ArrayList<? extends CampaignData> dataList, String id) {
    for (CampaignData campaignData : dataList) {
      if (campaignData.getId().equals(id)) return campaignData;
    }
    return null;
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
  private BigDecimal getTotalClickCost() {
    return totalClickCost;
  }

  /**
   * Sums up all the costs of each impression.
   *
   * @return Total cost of all impressions in campaign.
   */
  private BigDecimal getTotalImpressionCost() {
    return totalImpressionCost;
  }

  /**
   * Calculates the average cost per click using the total click cost divided by the number of
   * clicks.
   *
   * @return Average cost per click given in pence.
   */
  public BigDecimal getAvgCostPerClick() {
    return getTotalCost().divide(BigDecimal.valueOf(getClickDataCount()), 6, RoundingMode.HALF_UP);
  }

  /**
   * Calculates the click through rate in percentage using click and impression lists.
   *
   * @return Click through rate as a percentage.
   */
  public BigDecimal getClickThroughRate() {
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
    ArrayList<ClickData> clist = getClickDataList();
    BigDecimal sum = BigDecimal.valueOf(0);
    long count = 0L;
    for (ClickData clickData : clist) {
      if (getServerDataByID(clickData.getId()).hasConverted()) {
        sum.add(clickData.getClickCost());
        count++;
      }
    }
    if (count == 0) {
      return sum;
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
    return getTotalImpressionCost()
        .divide(BigDecimal.valueOf(impressionDataCount), 6, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(1000));
  }

  /**
   * Calculates the average acquisition cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public HashMap<String, BigDecimal> getDatedAcquisitionCostAverages() {
    HashMap<String, BigDecimal> outMap = new HashMap<>();
    HashMap<String, Integer> countMap = new HashMap<>();
    for (ClickData clickData : clickDataList) {
      if (getServerDataByID(clickData.getId()).hasConverted()) {
        getDatedClickCost(outMap, countMap, clickData);
      }
    }
    return getDatedCostOutput(outMap, countMap);
  }

  /**
   * Calculates the average impression cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public HashMap<String, BigDecimal> getDatedImpressionCostAverages() {
    HashMap<String, BigDecimal> outMap = new HashMap<>();
    HashMap<String, Integer> countMap = new HashMap<>();
    for (ImpressionData impressionData : impressionDataList) {
      String key = sdf.format(impressionData.getImpressionDate());
      BigDecimal oVal = outMap.get(key);
      Integer cVal = countMap.get(key);
      if (oVal != null) {
        oVal = oVal.add(impressionData.getImpressionCost());
        cVal++;
      } else {
        oVal = impressionData.getImpressionCost();
        cVal = 1;
      }
      outMap.put(key, oVal);
      countMap.put(key, cVal);
    }
    return getDatedCostOutput(outMap, countMap);
  }

  /**
   * Calculates the average click cost for each date.
   *
   * @return Hash Map containing the date in dd/MM/yyyy format as the key with the average cost for
   *     that date as the value.
   */
  public HashMap<String, BigDecimal> getDatedClickCostAverages() {
    HashMap<String, BigDecimal> outMap = new HashMap<>();
    HashMap<String, Integer> countMap = new HashMap<>();
    for (ClickData clickData : clickDataList) {
      getDatedClickCost(outMap, countMap, clickData);
    }
    return getDatedCostOutput(outMap, countMap);
  }

  /**
   * Returns the output for getDated methods.
   *
   * @param outMap This is the hash map containing dateString in dd/MM/yyyy with value of the sum of
   *     costs.
   * @param countMap This is the hash map containing dateString in dd/MM/yyyy with value of how many
   *     dates were counted.
   * @return Returns the averaged outMap. i.e outMap value is divided by corresponding countMap
   *     value.
   */
  private HashMap<String, BigDecimal> getDatedCostOutput(
      HashMap<String, BigDecimal> outMap, HashMap<String, Integer> countMap) {
    for (String key : outMap.keySet()) {
      outMap.put(
          key, outMap.get(key).divide(BigDecimal.valueOf(countMap.get(key)), RoundingMode.HALF_UP));
    }
    return outMap;
  }

  /**
   * Adds the total cost of click costs for each day and keeps track of a count of the amount of
   * days.
   *
   * @param outMap This is the hash map containing dateString in dd/MM/yyyy with value of the sum of
   *     costs.
   * @param countMap This is the hash map containing dateString in dd/MM/yyyy with value of how many
   *     dates were counted.
   * @param clickData The Click Data to add to the maps.
   */
  private void getDatedClickCost(
      HashMap<String, BigDecimal> outMap, HashMap<String, Integer> countMap, ClickData clickData) {
    String key = sdf.format(clickData.getClickDate());
    BigDecimal oVal = outMap.get(key);
    Integer cVal = countMap.get(key);
    if (oVal != null) {
      oVal = oVal.add(clickData.getClickCost());
      cVal++;
    } else {
      oVal = clickData.getClickCost();
      cVal = 1;
    }
    outMap.put(key, oVal);
    countMap.put(key, cVal);
  }

  /**
   * Calculates percentage of each age group.
   * @return Array of BigDecimals containing the percentage of eaah age group in the following order: [<25, 25-34, 35-44, 45-54, >54]
   */
  public BigDecimal[] getAgePercentage() {
    HashMap<String, Integer> countMap = new HashMap<>();
    int total = 0;
    for(ImpressionData impressionData:impressionDataList){
      incrementPercentageCounter(countMap, impressionData.getAge().toString());
      total++;
    }
    BigDecimal[] output = new BigDecimal[5];
    output[0] = percentageDivider(countMap, "<25", total);
    output[1] = percentageDivider(countMap, "25-34", total);
    output[2] = percentageDivider(countMap, "35-44", total);
    output[3] = percentageDivider(countMap, "45-54", total);
    output[4] = percentageDivider(countMap, ">54", total);
    return output;
  }

  /**
   * Calculates percentage of each gender.
   * @return Array of BigDecimals containing the percentage of gender in the following order: [Male, Female]
   */
  public BigDecimal[] getGenderPercentage() {
    HashMap<String, Integer> countMap = new HashMap<>();
    int total = 0;
    for(ImpressionData impressionData:impressionDataList){
      if(impressionData.getGender())
        incrementPercentageCounter(countMap, "Male");
      else
        incrementPercentageCounter(countMap, "Female");
      total++;
    }
    BigDecimal[] output = new BigDecimal[5];
    output[0] = percentageDivider(countMap, "Male", total);
    output[1] = percentageDivider(countMap, "Female", total);
    return output;
  }

  /**
   * Calculates percentage of each income group. Low=0,medium=1,high=2
   * @return Array of BigDecimals containing the percentage of each income group in the following order: [Low, Medium, High].
   */
  public BigDecimal[] getIncomePercentage() {
    HashMap<String, Integer> countMap = new HashMap<>();
    int total = 0;
    for(ImpressionData impressionData:impressionDataList){
      switch(impressionData.getIncome()){
        case 0:
          incrementPercentageCounter(countMap, "Low");
        case 1:
          incrementPercentageCounter(countMap, "Medium");
        case 2:
          incrementPercentageCounter(countMap, "High");
      }
      total++;
    }
    BigDecimal[] output = new BigDecimal[5];
    output[0] = percentageDivider(countMap, "Low", total);
    output[1] = percentageDivider(countMap, "Medium", total);
    output[2] = percentageDivider(countMap, "High", total);
    return output;
  }

  /**
   * Calculates percentage of each context. Blog=0,News=1,Shopping=2,Social Media=3.
   * @return Array of BigDecimals containing the percentage of each context in the following order: [Blog,News,Shopping,Social Media].
   */
  public BigDecimal[] getContextPercentage() {
    HashMap<String, Integer> countMap = new HashMap<>();
    int total = 0;
    for(ImpressionData impressionData:impressionDataList){
      switch(impressionData.getIncome()){
        case 0:
          incrementPercentageCounter(countMap, "Blog");
        case 1:
          incrementPercentageCounter(countMap, "News");
        case 2:
          incrementPercentageCounter(countMap, "Shopping");
        case 3:
          incrementPercentageCounter(countMap, "Social Media");
      }
      total++;
    }
    BigDecimal[] output = new BigDecimal[5];
    output[0] = percentageDivider(countMap, "Blog", total);
    output[1] = percentageDivider(countMap, "News", total);
    output[2] = percentageDivider(countMap, "Shopping", total);
    output[3] = percentageDivider(countMap, "Social Media", total);
    return output;
  }

  /**
   * Helps calculate percentage by tracking the running total and number of times a given key has
   * been seen.
   *
   * @param countMap This is the hash map containing dateString in dd/MM/yyyy with value the total
   *     number of times the given key has been seen.
   * @param key The key in countMap to increment.
   */
  private void incrementPercentageCounter(
      HashMap<String, Integer> countMap, String key) {
    Integer val = countMap.get(key);
    if(val != null)
      val++;
    else
      val = 1;
    countMap.put(key, val);
  }

  private BigDecimal percentageDivider(HashMap<String,Integer> countMap, String key, int total){
    return BigDecimal.valueOf(countMap.get(key)).divide(BigDecimal.valueOf(total), 6, RoundingMode.HALF_UP);
  }
}
