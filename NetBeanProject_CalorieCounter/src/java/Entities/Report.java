/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author s17
 */
@Entity
@Table(name = "report")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findByReportId", query = "SELECT r FROM Report r WHERE r.reportId = :reportId"),
    @NamedQuery(name = "Report.findByReportcarlorieConsumed", query = "SELECT r FROM Report r WHERE r.reportcarlorieConsumed = :reportcarlorieConsumed"),
    @NamedQuery(name = "Report.findByReportcarlorieBurned", query = "SELECT r FROM Report r WHERE r.reportcarlorieBurned = :reportcarlorieBurned"),
    @NamedQuery(name = "Report.findByReporttotalSteps", query = "SELECT r FROM Report r WHERE r.reporttotalSteps = :reporttotalSteps"),
    @NamedQuery(name = "Report.findByReportGoal", query = "SELECT r FROM Report r WHERE r.reportGoal = :reportGoal"),
    @NamedQuery(name = "Report.findByReportRemaining", query = "SELECT r FROM Report r WHERE r.reportRemaining = :reportRemaining"),
    @NamedQuery(name = "Report.finByUserId", query = "SELECT r FROM Report r WHERE r.userId.userId = :userId"),
    @NamedQuery(name = "Report.findByDietDate", query ="SELECT r FROM Report r WHERE r.dietDate = :dietDate"),
    @NamedQuery(name = "Report.findByUserNameAndGoal", query = "SELECT r FROM Report r JOIN r.userId u WHERE "
               + "r.reportGoal = :reportGoal AND u.userName= :userName")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "report_id")
    private Integer reportId;
    @Column(name = "diet_date")
    @Temporal(TemporalType.DATE)
    private Date dietDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "report_carlorieConsumed")
    private Double reportcarlorieConsumed;
    @Column(name = "report_carlorieBurned")
    private Double reportcarlorieBurned;
    @Column(name = "report_totalSteps")
    private Integer reporttotalSteps;
    @Column(name = "report_goal")
    private Double reportGoal;
    @Column(name = "report_remaining")
    private Double reportRemaining;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public Report() {
    }

    public Report(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Date getDietDate() {
        return dietDate;
    }

    public void setDietDate(Date dietDate) {
        this.dietDate = dietDate;
    }

    public Double getReportcarlorieConsumed() {
        return reportcarlorieConsumed;
    }

    public void setReportcarlorieConsumed(Double reportcarlorieConsumed) {
        this.reportcarlorieConsumed = reportcarlorieConsumed;
    }

    public Double getReportcarlorieBurned() {
        return reportcarlorieBurned;
    }

    public void setReportcarlorieBurned(Double reportcarlorieBurned) {
        this.reportcarlorieBurned = reportcarlorieBurned;
    }

    public Integer getReporttotalSteps() {
        return reporttotalSteps;
    }

    public void setReporttotalSteps(Integer reporttotalSteps) {
        this.reporttotalSteps = reporttotalSteps;
    }

    public Double getReportGoal() {
        return reportGoal;
    }

    public void setReportGoal(Double reportGoal) {
        this.reportGoal = reportGoal;
    }

    public Double getReportRemaining() {
        return reportRemaining;
    }

    public void setReportRemaining(Double reportRemaining) {
        this.reportRemaining = reportRemaining;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Report[ reportId=" + reportId + " ]";
    }
    
}
