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
    this.gender = getDemographicString(Demographic.Gender, gender);
    this.age = getDemographicString(Demographic.Age, age);
    this.income = getDemographicString(Demographic.Income, income);
    this.context = getDemographicString(Demographic.Context, context);
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

  /**
   * Maps byte value to the equivalent string for the specified demographic type.
   * @param type The type of demographic to use for mapping.
   * @param index The index to use for mapping.
   * @return The string value of the mapped demographic byte.
   */
  public static String getDemographicString(Demographic type, byte index) {
    String[] arr = getDemographicArray(type);
    if (index <0 || index >= arr.length) {
      return arr[0];
    }
    return arr[index];
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
