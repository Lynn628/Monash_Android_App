/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "food")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f"),
    @NamedQuery(name = "Food.findByFoodId", query = "SELECT f FROM Food f WHERE f.foodId = :foodId"),
    @NamedQuery(name = "Food.findByFoodName", query = "SELECT f FROM Food f WHERE f.foodName = :foodName"),
    @NamedQuery(name = "Food.findByFoodAmount", query = "SELECT f FROM Food f WHERE f.foodAmount = :foodAmount"),
    @NamedQuery(name = "Food.findByFoodquantityUnit", query = "SELECT f FROM Food f WHERE f.foodquantityUnit = :foodquantityUnit"),
    @NamedQuery(name = "Food.findByFoodFat", query = "SELECT f FROM Food f WHERE f.foodFat = :foodFat"),
    @NamedQuery(name = "Food.findByFoodCalorie", query = "SELECT f FROM Food f WHERE f.foodCalorie = :foodCalorie"),
    @NamedQuery(name = "Food.findByFoodFatAndFoodCalorie", query = "SELECT f FROM Food f WHERE f.foodFat = :foodFat AND f.foodCalorie = :foodCalorie")})
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "food_id")
    private Integer foodId;
    @Size(max = 128)
    @Column(name = "food_name")
    private String foodName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "food_amount")
    private Double foodAmount;
    @Size(max = 32)
    @Column(name = "food_quantityUnit")
    private String foodquantityUnit;
    @Column(name = "food_fat")
    private Integer foodFat;
    @Column(name = "food_calorie")
    private Integer foodCalorie;
    @OneToMany(mappedBy = "foodName")
    private Collection<Diet> dietCollection;
    @OneToMany(mappedBy = "foodquantityUint")
    private Collection<Diet> dietCollection1;

    public Food() {
    }

    public Food(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(Double foodAmount) {
        this.foodAmount = foodAmount;
    }

    public String getFoodquantityUnit() {
        return foodquantityUnit;
    }

    public void setFoodquantityUnit(String foodquantityUnit) {
        this.foodquantityUnit = foodquantityUnit;
    }

    public Integer getFoodFat() {
        return foodFat;
    }

    public void setFoodFat(Integer foodFat) {
        this.foodFat = foodFat;
    }

    public Integer getFoodCalorie() {
        return foodCalorie;
    }

    public void setFoodCalorie(Integer foodCalorie) {
        this.foodCalorie = foodCalorie;
    }

    @XmlTransient
    public Collection<Diet> getDietCollection() {
        return dietCollection;
    }

    public void setDietCollection(Collection<Diet> dietCollection) {
        this.dietCollection = dietCollection;
    }

    @XmlTransient
    public Collection<Diet> getDietCollection1() {
        return dietCollection1;
    }

    public void setDietCollection1(Collection<Diet> dietCollection1) {
        this.dietCollection1 = dietCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (foodId != null ? foodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.foodId == null && other.foodId != null) || (this.foodId != null && !this.foodId.equals(other.foodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Food[ foodId=" + foodId + " ]";
    }
    
}
