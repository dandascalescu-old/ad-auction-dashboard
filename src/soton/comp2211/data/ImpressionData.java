package soton.comp2211.data;

import java.util.Date;

public class ImpressionData extends CampaignData{
    private Date impressionDate;
    private String age;
    private int income,context;
    private double cost;
    private boolean gender;

    public ImpressionData(String id, Date impressionDate, String ageRange, int income, int context, double cost, boolean gender){
        this.id = id;
        this.impressionDate = impressionDate;
        this.age = ageRange;
        this.income = income;
        this.context = context;
        this.cost = cost;
        this.gender = gender;
    }

    public Date getImpressionDate() {
        return impressionDate;
    }

    public void setImpressionDate(Date impressionDate) {
        this.impressionDate = impressionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getContext() {
        return context;
    }

    public void setContext(int context) {
        this.context = context;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
