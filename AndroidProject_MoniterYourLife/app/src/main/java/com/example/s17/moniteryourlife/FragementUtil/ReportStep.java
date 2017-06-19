package com.example.s17.moniteryourlife.FragementUtil;

/**
 * Created by s17 on 4/22/2016.
 */
public class ReportStep {
    String userName;
    String dietDate;
    String totalSteps;
    public ReportStep(String userName, String dietDate, String totalSteps){
        this.userName = userName;
        this.dietDate = dietDate;
        this.totalSteps = totalSteps;
    }

    public String getDietDate() {
        return dietDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTotalSteps() {
        return totalSteps;
    }

    public void setDietDate(String dietDate) {
        this.dietDate = dietDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }
}
