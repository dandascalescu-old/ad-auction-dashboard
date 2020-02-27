package soton.comp2211;

import soton.comp2211.data.*;

public class Campaign {
  private String campaignID;
  private ImpressionData[] impressionDataList;
  private ServerData[] serverDataList;
  private ClickData[] clickDataList;

    public String getCampaignID() {
        return campaignID;
    }

    public ClickData[] getClickDataList() {
        return clickDataList;
    }

    public ImpressionData[] getImpressionDataList() {
        return impressionDataList;
    }

    public ServerData[] getServerDataList() {
        return serverDataList;
    }

    public ClickData getClickDataByID(String id){
        return (ClickData) getDataByID(getServerDataList(), id);
    }

    public ImpressionData getImpressionDataByID(String id){
        return (ImpressionData) getDataByID(getServerDataList(), id);
    }

    public ServerData getServerDataByID(String id){
        return (ServerData)getDataByID(getServerDataList(), id);
    }

    private CampaignData getDataByID(CampaignData[] dataList, String id){
        for(CampaignData campaignData:dataList){
            if(campaignData.getId().equals(id))
                return campaignData;
        }
        return null;
    }
}
