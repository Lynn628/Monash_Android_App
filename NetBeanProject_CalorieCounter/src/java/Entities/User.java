/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author s17
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByUserAge", query = "SELECT u FROM User u WHERE u.userAge = :userAge"),
    @NamedQuery(name = "User.findByUserHeight", query = "SELECT u FROM User u WHERE u.userHeight = :userHeight"),
    @NamedQuery(name = "User.findByUserWeight", query = "SELECT u FROM User u WHERE u.userWeight = :userWeight"),
    @NamedQuery(name = "User.findByUserGender", query = "SELECT u FROM User u WHERE u.userGender = :userGender"),
    @NamedQuery(name = "User.findByUserlevelOfActivity", query = "SELECT u FROM User u WHERE u.userlevelOfActivity = :userlevelOfActivity"),
    @NamedQuery(name = "User.findByUserstepPerMiles", query = "SELECT u FROM User u WHERE u.userstepPerMiles = :userstepPerMiles")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Size(max = 255)
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_age")
    private Integer userAge;
    @Column(name = "user_height")
    private Integer userHeight;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "user_weight")
    private Float userWeight;
    @Size(max = 2)
    @Column(name = "user_gender")
    private String userGender;
    @Column(name = "user_levelOfActivity")
    private Integer userlevelOfActivity;
    @Column(name = "user_stepPerMiles")
    private Integer userstepPerMiles;
    @OneToMany(mappedBy = "userId")
    private Collection<Report> reportCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Diet> dietCollection;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Integer getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(Integer userHeight) {
        this.userHeight = userHeight;
    }

    public Float getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(Float userWeight) {
        this.userWeight = userWeight;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Integer getUserlevelOfActivity() {
        return userlevelOfActivity;
    }

    public void setUserlevelOfActivity(Integer userlevelOfActivity) {
        this.userlevelOfActivity = userlevelOfActivity;
    }

    public Integer getUserstepPerMiles() {
        return userstepPerMiles;
    }

    public void setUserstepPerMiles(Integer userstepPerMiles) {
        this.userstepPerMiles = userstepPerMiles;
    }

    @XmlTransient
    public Collection<Report> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<Report> reportCollection) {
        this.reportCollection = reportCollection;
    }

    @XmlTransient
    public Collection<Diet> getDietCollection() {
        return dietCollection;
    }

    public void setDietCollection(Collection<Diet> dietCollection) {
        this.dietCollection = dietCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.User[ userId=" + userId + " ]";
    }
    
}
