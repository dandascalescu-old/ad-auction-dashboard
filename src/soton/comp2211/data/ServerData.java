package soton.comp2211.data;

import java.util.Date;

public class ServerData extends CampaignData {
  private Date entryDate, exitDate;
  private String pagesViewed;
  private boolean conversion;

  public ServerData(
      String id, Date entryDate, Date exitDate, String pagesViewed, boolean conversion) {
    this.id = id;
    this.entryDate = entryDate;
    this.exitDate = exitDate;
    this.pagesViewed = pagesViewed;
    this.conversion = conversion;
  }

  public ServerData(String id, Date entryDate, String pagesViewed, boolean conversion) {
    this.id = id;
    this.entryDate = entryDate;
    this.pagesViewed = pagesViewed;
    this.conversion = conversion;
  }

  public Date getEntryDate() {
    return entryDate;
  }

  public void setEntryDate(Date entryDate) {
    this.entryDate = entryDate;
  }

  public Date getExitDate() {
    return exitDate;
  }

  public void setExitDate(Date exitDate) {
    this.exitDate = exitDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPagesViewed() {
    return pagesViewed;
  }

  public void setPagesViewed(String pagesViewed) {
    this.pagesViewed = pagesViewed;
  }

  public boolean isConversion() {
    return conversion;
  }

  public void setConversion(boolean conversion) {
    this.conversion = conversion;
  }
}
