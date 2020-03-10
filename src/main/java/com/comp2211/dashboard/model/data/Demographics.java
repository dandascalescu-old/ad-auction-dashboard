package com.comp2211.dashboard.model.data;

public class Demographics {

  public static enum Demographic { Gender, Age, Income, Context };

  public static final String[] GenderTypes = { "Male", "Female" };
  public static final String[] AgeTypes = { "<25", "25-34", "35-44", "45-54", ">54" };
  public static final String[] IncomeTypes = { "Low", "Medium", "High" };
  public static final String[] ContextTypes = { "Blog", "News", "Shopping", "Social Media" };

  private String gender, age, income, context;

  /**
   * Constructor for storing demographic data.
   */
  public Demographics(byte gender, byte age, byte income, byte context) {
    this.gender = byteToString(gender, GenderTypes);
    this.age = byteToString(age, AgeTypes);
    this.income = byteToString(income, IncomeTypes);
    this.context = byteToString(context, ContextTypes);
  }

  public String getGender() {
    return gender;
  }
  public String getAge() {
    return age;
  }
  public String getIncome() {
    return income;
  }
  public String getContext() {
    return context;
  }

  public static String byteToString (byte val, String[] arr) {
    if (val <0 || val >= arr.length) {
      return arr[0];
    }
    return arr[val];
  }

  public static String[] getDemographicArray(Demographic type) {
    switch(type) {
      case Age:
        return AgeTypes;
      case Income:
        return IncomeTypes;
      case Context:
        return ContextTypes;
      default:
        return GenderTypes;
    }
  }
}
