package com.comp2211.dashboard.model.data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** A type of campaign data. Used to store information about clicks. */
public class ClickData extends CampaignData {
  private Timestamp clickDate;
  private BigDecimal clickCost;

  /**
   * Constructor for storing click data.
   *
   * @param id {@link CampaignData#id}
   * @param clickDate Date and time the advert was clicked. Uses Java Date type.
   * @param clickCost The cost of the click given in pence.
   */
  public ClickData(String id, Timestamp clickDate, BigDecimal clickCost) {
    super(id);
    this.clickDate = clickDate;
    this.clickCost = clickCost;
  }

  /**
   * Returns clickDate, which is the date and time an advert is clicked.
   *
   * @return clickDate
   */
  public Timestamp getClickDate() {
    return clickDate;
  }

  /**
   * Returns the cost of the click in pence.
   *
   * @return clickCost is the amount of money charged per click in pence.
   */
  public BigDecimal getClickCost() {
    return clickCost;
  }
}
