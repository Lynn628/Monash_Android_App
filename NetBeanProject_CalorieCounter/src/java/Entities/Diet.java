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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author s17
 */
@Entity
@Table(name = "diet")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Diet.findAll", query = "SELECT d FROM Diet d"),
    @NamedQuery(name = "Diet.findByDietId", query = "SELECT d FROM Diet d WHERE d.dietId = :dietId"),
    @NamedQuery(name = "Diet.findByFoodAmount", query = "SELECT d FROM Diet d WHERE d.foodAmount = :foodAmount"),
    @NamedQuery(name = "Diet.findByDietDate", query = "SELECT d FROM Diet d WHERE d.dietDate = :dietDate"),
    @NamedQuery(name = "Diet.findByDietTime", query = "SELECT d FROM Diet d WHERE d.dietTime = :dietTime"),
    @NamedQuery(name = "Diet.findByFoodName", query = "SELECT d FROM Diet d WHERE d.foodName.foodName = :foodName"),
    @NamedQuery(name = "Diet.findByUserId", query = "SELECT d FROM Diet d WHERE d.userId.userId = :userId"),
    @NamedQuery(name = "Diet.findByFoodQuantityUnit", query = "SELECT d FROM Diet d WHERE d.foodquantityUint.foodquantityUnit = :foodQuantityUnit")})
public class Diet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "diet_id")
    private Integer dietId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "food_amount")
    private Double foodAmount;
    @Column(name = "diet_date")
    @Temporal(TemporalType.DATE)
    private Date dietDate;
    @Column(name = "diet_time")
    @Temporal(TemporalType.TIME)
    private Date dietTime;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "food_name", referencedColumnName = "food_name")
    @ManyToOne
    private Food foodName;
    @JoinColumn(name = "food_quantityUint", referencedColumnName = "food_quantityUnit")
    @ManyToOne
    private Food foodquantityUint;

    public Diet() {
    }

    public Diet(Integer dietId) {
        this.dietId = dietId;
    }

    public Integer getDietId() {
        return dietId;
    }

    public void setDietId(Integer dietId) {
        this.dietId = dietId;
    }

    public Double getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(Double foodAmount) {
        this.foodAmount = foodAmount;
    }

    public Date getDietDate() {
        return dietDate;
    }

    public void setDietDate(Date dietDate) {
        this.dietDate = dietDate;
    }

    public Date getDietTime() {
        return dietTime;
    }

    public void setDietTime(Date dietTime) {
        this.dietTime = dietTime;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Food getFoodName() {
        return foodName;
    }

    public void setFoodName(Food foodName) {
        this.foodName = foodName;
    }

    public Food getFoodquantityUint() {
        return foodquantityUint;
    }

    public void setFoodquantityUint(Food foodquantityUint) {
        this.foodquantityUint = foodquantityUint;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dietId != null ? dietId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Diet)) {
            return false;
        }
        Diet other = (Diet) object;
        if ((this.dietId == null && other.dietId != null) || (this.dietId != null && !this.dietId.equals(other.dietId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Diet[ dietId=" + dietId + " ]";
    }
    
}
