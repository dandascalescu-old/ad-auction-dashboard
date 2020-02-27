package com.comp2211.dashboard.data;

import java.util.Date;

/** A type of campaign data. Used to store information about clicks. */
public class ClickData extends CampaignData {
  private Date clickDate;
  private double clickCost;

  /**
   * Constructor for storing click data.
   *
   * @param id {@link CampaignData#id}
   * @param clickDate Date and time the advert was clicked. Uses Java Date type.
   * @param clickCost The cost of the click given in pence.
   */
  public ClickData(String id, Date clickDate, double clickCost) {
    super(id);
    this.clickDate = clickDate;
    this.clickCost = clickCost;
  }

  /**
   * Returns clickDate, which is the date and time an advert is clicked.
   *
   * @return clickDate
   */
  public Date getClickDate() {
    return clickDate;
  }

  /**
   * Sets clickDate, which is the date and time an advert is clicked.
   *
   * @param clickDate Must be of using Java Date type.
   */
  public void setClickDate(Date clickDate) {
    this.clickDate = clickDate;
  }

  /**
   * Returns the cost of the click in pence.
   *
   * @return clickCost is the amount of money charged per click in pence.
   */
  public double getClickCost() {
    return clickCost;
  }

  /**
   * Sets the click cost.
   *
   * @param clickCost Click cost in pence.
   */
  public void setClickCost(double clickCost) {
    this.clickCost = clickCost;
  }
}
