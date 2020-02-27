package soton.comp2211.data;

/** Superclass for types of campaign data. Stores properties shared with all types of data. */
public class CampaignData {
  /**
   * id Campaign data ID. Unique to data entries and is different to Campaign ID. Different * types
   * of data may be linked to other data, i.e Click Data may have the same ID as * Impression Data
   * to show they're linked.
   */
  String id;

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

  /**
   * Sets the campaign data ID. T
   *
   * @param id {@link #id}
   */
  protected void setId(String id) {
    this.id = id;
  }
}
