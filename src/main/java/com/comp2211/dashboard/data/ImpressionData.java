package com.comp2211.dashboard.data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** A type of campaign data. Used to store information about impressions. */
public class ImpressionData extends CampaignData {
  private Timestamp impressionDate;
  private String age;
  private boolean gender;
  private byte income, context;
  private BigDecimal impressionCost;

  public enum Gender { Male, Female };
  public enum Income { Low, Medium, High };
  public enum Context { Blog, News, Shopping, Social_Media };
  
  
  /**
   * Constructor for storing impression data.
   *
   * @param id {@link CampaignData#id}
   * @param impressionDate The date/time of the impression. This is given as a Java Date object.
   * @param ageRange The age range of this impression. i.e under 25s will be <25 Todo: Should we
   *     change this? In the db it is currently given as a string...
   * @param income The type of income of the user who was given the impression. This is given as
   *     follows: Low=0,Medium=1,High=2.
   * @param context The type of context where the user has seen the impression. E.g.
   *     Blog=0,News=1,Shopping=2,Social Media=3.
   * @param impressionCost The cost of the impression in pence.
   * @param gender The gender of the user, given as follows: Male=0,Female=1.
   */
  public ImpressionData(
      String id,
      Timestamp impressionDate,
      String ageRange,
      byte income,
      byte context,
      BigDecimal impressionCost,
      boolean gender) {
    super(id);
    this.impressionDate = impressionDate;
    this.age = ageRange;
    this.income = income;
    this.context = context;
    this.impressionCost = impressionCost;
    this.gender = gender;
  }

  /**
   * Returns impression date.
   *
   * @return impressionDate is the date/time of the impression returned as a Java Date object.
   */
  public Timestamp getImpressionDate() {
    return impressionDate;
  }

  public void setImpressionDate(Timestamp impressionDate) {
    this.impressionDate = impressionDate;
  }

  /**
   * Returns age range.
   *
   * @return age range is the user demographic of age. E.g. under 25s will be <25
   */
  public String getAge() {
    return age;
  }

  /**
   * Sets the age range of the user given the impression
   *
   * @param age The age range for the given impression. E.g. <25.
   */
  public void setAge(String age) {
    this.age = age;
  }

  /**
   * Returns income group.
   *
   * @return income is the type of income of the user who was given the impression. This is given as
   *     follows: Low=0,Medium=1,High=2.
   */
  public byte getIncome() {
    return income;
  }

  /**
   * Sets the income group of the user given the impression.
   *
   * @param income This is given as follows: Low=0,Medium=1,High=2.
   */
  public void setIncome(byte income) {
    this.income = income;
  }

  /**
   * Returns context type.
   *
   * @return context is the type of context where the user has seen the impression. E.g.
   *     Blog=0,News=1,Shopping=2,Social Media=3.
   */
  public byte getContext() {
    return context;
  }

  /**
   * Sets the context of where the user was given the impression.
   *
   * @param context This is given as follows: Blog=0,News=1,Shopping=2,Social Media=3.
   */
  public void setContext(byte context) {
    this.context = context;
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
   * Sets the impression cost.
   *
   * @param impressionCost Click cost in pence.
   */
  public void setImpressionCost(BigDecimal impressionCost) {
    this.impressionCost = impressionCost;
  }

  /**
   * Returns the gender of the user given the impression.
   *
   * @return gender is the gender of the user, given as follows: Male=0,Female=1.
   */
  public boolean getGender() {
    return gender;
  }

  /**
   * Sets the gender of the user given the impression.
   *
   * @param gender This is given as follows: Male=0,Female=1.
   */
  public void setGender(boolean gender) {
    this.gender = gender;
  }
}
