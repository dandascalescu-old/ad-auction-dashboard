package com.comp2211.dashboard.data;

import java.sql.Timestamp;

/** A type of campaign data. Used to store information about server. */
public class ServerData extends CampaignData {
  private Timestamp entryDate, exitDate;
  private byte pagesViewed;
  private boolean converted;

  /**
   * Constructor for storing server data.
   *
   * @param id {@link CampaignData#id}
   * @param entryDate The date/time of the initial entry to the server. This is given as a  Java Date
   *     object.
   * @param exitDate The date/time when user has exited the server. This is given as a Java Date object.
   *     Not all exits may be logged, so this may be null!
   * @param pagesViewed The number of pages the user has gone through or accessed within the server.
   * @param converted Boolean denoting if the user click has led to a conversion.
   */
  public ServerData(
      String id, Timestamp entryDate, Timestamp exitDate, byte pagesViewed, boolean converted) {
    super(id);
    this.entryDate = entryDate;
    this.exitDate = exitDate;
    this.pagesViewed = pagesViewed;
    this.converted = converted;
  }

  /**
   * Returns entry date.
   *
   * @return entryDate is the date/time of the initial entry to the server in Java Date type.
   */
  public Timestamp getEntryDate() {
    return entryDate;
  }

  /**
   * Sets entry date, which is the date/time of the initial entry to the server.
   *
   * @param entryDate Must be of using Java Date type.
   */
  public void setEntryDate(Timestamp entryDate) {
    this.entryDate = entryDate;
  }

  /**
   * Returns exit date, which is the date/time when user has exited the server.
   *
   * @return exitDate returned. Not all exits may be logged, so this may be null!
   */
  public Timestamp getExitDate() {
    return exitDate;
  }

  /**
   * Sets exit date, which is the date and time an advert is clicked.
   *
   * @param exitDate Must be of using Java Date type.
   */
  public void setExitDate(Timestamp exitDate) {
    this.exitDate = exitDate;
  }

  /**
   * Returns number of pages viewed.
   *
   * @return pagesViewed is the number of pages the user has gone through or accessed within the
   *     server.
   */
  public byte getPagesViewed() {
    return pagesViewed;
  }

  /**
   * Sets pages viewed.
   *
   * @param pagesViewed The number of pages the user has gone through or accessed within the server.
   */
  public void setPagesViewed(byte pagesViewed) {
    this.pagesViewed = pagesViewed;
  }

  /**
   * Returns if the the particular server entry led to a conversion.
   *
   * @return True if converted. False if not.
   */
  public boolean hasConverted() {
    return converted;
  }

  /**
   * Sets conversion boolean.
   *
   * @param converted True if converted. False if not.
   */
  public void setConversion(boolean converted) {
    this.converted = converted;
  }
}
