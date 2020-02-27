package soton.comp2211;

import soton.comp2211.data.*;

import java.util.ArrayList;

public class Campaign {
  private String campaignID;
  private ArrayList<ImpressionData> impressionDataList;
  private ArrayList<ServerData> serverDataList;
  private ArrayList<ClickData> clickDataList;

    /**
     * Constructor for a campaign.
     * @param campaignID The unique identifier for a given campaign. Must be given for each campaign.
     */
  public Campaign(String campaignID) {
    this.campaignID = campaignID;
  }

  /**
   * Returns impression data list for the campaign.
   *
   * @return Impression data list from the campaign.
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
   * @return Server data list from the campaign.
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
   * @return Click data list from the campaign.
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
   * @return Click data with matched ID.
   */
  public ClickData getClickDataByID(String id) {
    return (ClickData) getDataByID(getServerDataList(), id);
  }

    /**
     * Retrieves impression data from given ID.
     *
     * @return Impression data with matched ID.
     */
  public ImpressionData getImpressionDataByID(String id) {
    return (ImpressionData) getDataByID(getServerDataList(), id);
  }

    /**
     * Retrieves server data from given ID.
     *
     * @return Server data with matched ID.
     */
  public ServerData getServerDataByID(String id) {
    return (ServerData) getDataByID(getServerDataList(), id);
  }

    /**
     * Generic method for grabbing campaign data from given ID.
     *
     * @param dataList The data list to scan/query. Must be a subclass of Campaign Data.
     * @param id The ID to find.
     * @return Campaign Data with matched ID.
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
     * Adds given impression data to campaign.
     *
     * @param impressionData Impression data object to add to campaign.
     */
  public void addImpressionData(ImpressionData impressionData) {
    impressionDataList.add(impressionData);
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
     * Calculates the average cost per click using the Click Data List.
     *
     * @return Average cost per click.
     */
  public double getAvgCostPerClick() {
    ArrayList<ClickData> list = getClickDataList();
    double sum = 0;
    for (ClickData clickData : list) {
      sum += clickData.getClickCost();
    }
    return sum / list.size();
  }

    /**
     * Calculates the click through rate in percentage using click and impression lists.
     *
     * @return Click through rate as a percentage.
     */
  public double getClickThroughRate(){
      return (double)(clickDataList.size()/impressionDataList.size())*100;
  }
}
