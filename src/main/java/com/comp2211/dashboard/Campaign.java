package com.comp2211.dashboard;

import com.comp2211.dashboard.data.*;
import com.comp2211.dashboard.io.DatabaseManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Campaign object used to represent each individual campaign. Contains impression, click and server
 * info.
 */
public class Campaign {
  private String campaignID;
  private ArrayList<ImpressionData> impressionDataList;
  private ArrayList<ServerData> serverDataList;
  private ArrayList<ClickData> clickDataList;
  
  private BigDecimal totalClickCost, totalImpressionCost = null;
  private long clickDataCount, impressionDataCount, serverDataCount = 0L;

  /**
   * Constructor for a campaign.
   *
   * @param campaignID The unique identifier for a given campaign. Must be given for each campaign.
   */
  public Campaign(String campaignID) {
    this.campaignID = campaignID;
  }

  public void cacheData(int limit) {
    clickDataCount = DatabaseManager.retrieveDataCount(DatabaseManager.Table.click_table);
    impressionDataCount = DatabaseManager.retrieveDataCount(DatabaseManager.Table.impression_table);
	serverDataCount = DatabaseManager.retrieveDataCount(DatabaseManager.Table.server_table);
	
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
   * @return Total cost of all click in campaign.
   */
  private BigDecimal getTotalClickCost(){
    return totalClickCost;
  }

  /**
   * Sums up all the costs of each impression.
   * @return Total cost of all impressions in campaign.
   */
  private BigDecimal getTotalImpressionCost(){
    return totalImpressionCost;
  }

  /**
   * Calculates the average cost per click using the total click cost divided by the number of clicks.
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
   * Calculates/estimates the cost per thousand impression by calculating the average cost of a single impression multiplied by 1000
   *
   * @return The average cost per acquisition given in pence.
   */
  public BigDecimal getCostPerThousandImpressions(){
    return getTotalImpressionCost().divide(BigDecimal.valueOf(impressionDataCount), 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(1000));
  }
}
