package com.example.s17.moniteryourlife.FragementUtil;

import java.util.Date;

/**
 * Created by s17 on 4/21/2016.
 */
public class Report {

    private String dietDate;
    private String reportcarlorieConsumed;
    private String reportcarlorieBurned;
    private String reporttotalSteps;
    private String reportGoal;
    private String reportRemaining;
    private String userName;

    public Report(String userName, String dietDate){
        this.userName = userName;
        this.dietDate = dietDate;
    }

    public Report(String userName, String dietDate, String reportGoal) {
        this.userName = userName;
        this.dietDate = dietDate;
        this.reportGoal = reportGoal;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getDietDate() {
        return dietDate;
    }

    public void setReportcarlorieBurned(String reportcarlorieBurned) {
        this.reportcarlorieBurned = reportcarlorieBurned;
    }

    public String getReportcarlorieBurned() {
        return reportcarlorieBurned;
    }

    public void setDietDate(String dietDate) {
        this.dietDate = dietDate;
    }

    public String getReportcarlorieConsumed() {
        return reportcarlorieConsumed;
    }

    public String getReportGoal() {
        return reportGoal;
    }

    public String getReportRemaining() {
        return reportRemaining;
    }

    public String getReporttotalSteps() {
        return reporttotalSteps;
    }

    public void setReportcarlorieConsumed(String reportcarlorieConsumed) {
        this.reportcarlorieConsumed = reportcarlorieConsumed;
    }

    public void setReportGoal(String reportGoal) {
        this.reportGoal = reportGoal;
    }

    public void setReportRemaining(String reportRemaining) {
        this.reportRemaining = reportRemaining;
    }

    public void setReporttotalSteps(String reporttotalSteps) {
        this.reporttotalSteps = reporttotalSteps;
    }

}
