package com.comp2211.dashboard.model.data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** A type of campaign data. Used to store information about impressions. */
public class ImpressionData extends CampaignData {
  private Timestamp impressionDate;
  private BigDecimal impressionCost;
  private Demographics demographics;

  /**
   * Constructor for storing impression data.
   *
   * @param id {@link CampaignData#id}
   * @param impressionDate The date/time of the impression. This is given as a Java Date object.
   * @param impressionCost The cost of the impression in pence.
   * @param demographics The demographics object stores data such as gender, age, income and context.
   */
  public ImpressionData(String id, Timestamp impressionDate, BigDecimal impressionCost, Demographics demographics) {
    super(id);
    this.impressionDate = impressionDate;
    this.impressionCost = impressionCost;
    this.demographics = demographics;
  }

  /**
   * Returns impression date.
   *
   * @return impressionDate is the date/time of the impression returned as a Java Date object.
   */
  public Timestamp getImpressionDate() {
    return impressionDate;
  }

  /**
   * Returns cost of impression.
   *
   * @return impressionCost is the cost of the impression in pence.
   */
  public BigDecimal getImpressionCost() {
    return impressionCost;
  }

  /**
   * Returns the reference to the demographic object that stores data such as gender, age, income and context.
   *
   * @return demographics
   */
  public Demographics getDemographics() {
    return demographics;
  }
}
