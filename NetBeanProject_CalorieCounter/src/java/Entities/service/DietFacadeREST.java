/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.service;

import Entities.Diet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import Entities.User;
import Entities.Food;
import java.util.Date;
/**
 *
 * @author s17
 */
@Stateless
@Path("entities.diet")
public class DietFacadeREST extends AbstractFacade<Diet> {

    @PersistenceContext(unitName = "CalorieCounterPU")
    private EntityManager em;

    public DietFacadeREST() {
        super(Diet.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Diet entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Diet entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Diet find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Diet> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Diet> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     @GET
    @Path("findByDietId/{dietId}")
    @Produces({"application/json"})
    public List<Diet> findByDietId(@PathParam("dietId")Integer dietId){
        Query query = em.createNamedQuery("Diet.findByDietId");
        query.setParameter("dietId",dietId);
        return query.getResultList();
    }  


    @GET
    @Path("findByFoodAmount/{foodAmount}")
    @Produces({"application/json"})
    public List<Diet> findByFoodAmount(@PathParam("foodAmount")Double foodAmount ){
        Query query = em.createNamedQuery("Diet.findByFoodAmount");
        query.setParameter("foodAmount",foodAmount);
        return query.getResultList();
    }  

    @GET
    @Path("findByDietDate/{dietDate}")
    @Produces({"application/json"})
    public List<Diet> findByDietDate(@PathParam("dietDate")String dietDate ) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date javaDate = dateFormat.parse(dietDate); 
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
        Query query = em.createNamedQuery("Diet.findByDietDate");
        query.setParameter("dietDate",sqlDate);
        return query.getResultList();
    }  

    @GET
    @Path("findByDietTime/{dietTime}")
    @Produces({"application/json"})
    public List<Diet> findByDietTime(@PathParam("dietTime")String dietTime ) {
        java.sql.Time sqlTime = java.sql.Time.valueOf(dietTime);
        Query query = em.createNamedQuery("Diet.findByDietTime");
        query.setParameter("dietTime",sqlTime);
        return query.getResultList();
    }  
    
    
   @GET
   @Path("findByFoodName/{foodName}")
   @Produces("application/json")
   public List<Diet> findByFoodName(@PathParam("foodName")String foodName){
       Query query = em.createNamedQuery("Diet.findByFoodName");
       query.setParameter("foodName", foodName);
       return query.getResultList();
   }
   //diet table reference attribute userId 
   @GET
   @Path("findByUserId/{userId}")
   @Produces("application/json")
   public List<Diet> findByUserId(@PathParam("userId")Integer userId){
       Query query = em.createNamedQuery("Diet.findByUserId");
       query.setParameter("userId", userId);
       return query.getResultList();
   }

   //diet table references attribute foodquantityUnit
   @GET
   @Path("findByFoodQuantiyUnit/{foodQuantityUnit}")
   @Produces("application/json")
   public List<Diet> findByQuantityUnit(@PathParam("foodQuantityUnit")String foodQuantityUnit){
       Query query = em.createNamedQuery("Diet.findByFoodQuantityUnit");
       query.setParameter("foodQuantityUnit", foodQuantityUnit);
       return query.getResultList();
   }
   
   //Dynamic query of combined attribute foodDietDate and foodDietTime
     @GET
    @Path("findByDietDateAndTime/{dietDate}/{dietTime}")
    @Produces({"application/json"})
    public List<Diet> findByFoodDietDateAndTime(@PathParam("dietDate")String dietDate,
            @PathParam("dietTime")String dietTime){
            java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
            java.sql.Time sqlTime = java.sql.Time.valueOf(dietTime);
            TypedQuery<Diet> query = 
                    em.createQuery("SELECT d FROM Diet d WHERE d.dietDate =:dietDate "
                             + "AND d.dietTime = :dietTime", Diet.class);
            query.setParameter("dietDate",sqlDate);
            query.setParameter("dietTime",sqlTime);
            return query.getResultList();
        }
     @GET
    @Path("findByUserNameAndDietDate/{userName}/{dietDate}")
    @Produces({"application/json"})
    public List<Diet> findByUserNameAndDietDate(@PathParam("userName")String userName,
            @PathParam("dietDate")String dietDate ){
            java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
            TypedQuery<Diet> query = 
                    em.createQuery("SELECT d FROM Diet d WHERE d.dietDate = :dietDate "
                             + "AND d.userId.userName = :userName", Diet.class);
            query.setParameter("userName",userName);
            query.setParameter("dietDate",sqlDate);
            return query.getResultList();
        }
   /**
    * Add food to diet table
    * @param userName
    * @param dietDate
     * @param foodName
    * @return 
    */
    @GET
    @Path("addFoodToDiet/{userName}/{dietDate}/{foodName}/{foodAmount}")
    @Produces({"application/json"})
    public String addFoodToDiet(@PathParam("userName")String userName,
             @PathParam("dietDate")String dietDate, @PathParam("foodName")String foodName,
             @PathParam("foodAmount")String foodAmount){
            String foodNameSpace = foodName.replace("-", " ");
            java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
                TypedQuery<User> query1 = 
                    em.createQuery("SELECT u FROM User u WHERE u.userName = :userName", User.class);
            query1.setParameter("userName",userName);
            TypedQuery<Food> query2 = 
                    em.createQuery("SELECT f FROM Food f WHERE f.foodName = :foodName", Food.class);
            query2.setParameter("foodName",foodNameSpace);
            Diet userDiet = new Diet();
            userDiet.setDietId( findAll().size()+2);
            userDiet.setUserId(query1.getResultList().get(0));
            userDiet.setDietDate(sqlDate);
            userDiet.setFoodName(query2.getResultList().get(0));
            Double amount = Double.parseDouble(foodAmount);
            userDiet.setFoodAmount(amount);
            userDiet.setFoodquantityUint(query2.getResultList().get(0));
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            String time = formatTime.format(new java.util.Date());
            java.sql.Time sqlTime = java.sql.Time.valueOf(time);
            userDiet.setDietTime(sqlTime);
            super.create(userDiet);
            return "OK";
        }
}
