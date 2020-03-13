package com.comp2211.dashboard.model.data;

/** Superclass for types of campaign data. Stores properties shared with all types of data. */
public class CampaignData {
  /**
   * id Campaign data ID. Unique to data entries and is different to Campaign ID. Different * types
   * of data may be linked to other data, i.e Click Data may have the same ID as * Impression Data
   * to show they're linked.
   */
  private String id;

  /**
   * Constructor for storing campaign data.
   *
   * @param id {@link #id}
   */
  CampaignData(String id) {
    this.id = id;
  }

  /**
   * Returns the ID assigned.
   *
   * @return ID assigned.
   */
  public String getId() {
    return id;
  }
}

