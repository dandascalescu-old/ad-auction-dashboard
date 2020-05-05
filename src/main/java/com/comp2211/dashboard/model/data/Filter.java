package com.comp2211.dashboard.model.data;

import static com.comp2211.dashboard.model.data.Demographics.getDemographicInt;

import com.comp2211.dashboard.model.data.Demographics.Demographic;
import java.time.LocalDate;

public class Filter {
  public int campaignID;
  public LocalDate startDate, endDate;
  public int gender, age, income, context;

  public Filter() {
    this.gender = -1;
    this.age = -1;
    this.income = -1;
    this.context = -1;
  }

  public Filter(int campaignID) {
    this();
    this.campaignID = campaignID;
  }

  public Filter(LocalDate startDate, LocalDate endDate, String gender, String age, String income, String context) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.gender = getDemographicInt(Demographic.Gender, gender);
    this.age = getDemographicInt(Demographic.Age, age);
    this.income = getDemographicInt(Demographic.Income, income);
    this.context = getDemographicInt(Demographic.Context, context);
  }

  public void setCampaignID(int campaignID) {
    this.campaignID = campaignID;
  }

  public int getCampaignID(){
    return campaignID;
  }

  public boolean isEqualTo(Filter other) {
    return (this.startDate == null ? other.startDate == null : (other.startDate != null && this.startDate.isEqual(other.startDate))) &&
        (this.endDate == null ? other.endDate == null : (other.startDate != null && this.endDate.isEqual(other.endDate))) &&
        (this.gender == other.gender) &&
        (this.age == other.age) &&
        (this.income == other.income) &&
        (this.context == other.context);
  }
}
