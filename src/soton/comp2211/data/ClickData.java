package soton.comp2211.data;

import java.util.Date;

public class ClickData extends CampaignData {
  private Date clickDate;
  private double clickCost;

  public ClickData(String id, Date clickDate, double clickCost) {
    this.id = id;
    this.clickDate = clickDate;
    this.clickCost = clickCost;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getClickDate() {
    return clickDate;
  }

  public void setClickDate(Date clickDate) {
    this.clickDate = clickDate;
  }

  public double getClickCost() {
    return clickCost;
  }

  public void setClickCost(double clickCost) {
    this.clickCost = clickCost;
  }
}
