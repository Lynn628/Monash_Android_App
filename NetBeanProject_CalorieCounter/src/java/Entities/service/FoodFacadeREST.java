/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.service;

import Entities.Food;
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

/**
 *
 * @author s17
 */
@Stateless
@Path("entities.food")
public class FoodFacadeREST extends AbstractFacade<Food> {

    @PersistenceContext(unitName = "CalorieCounterPU")
    private EntityManager em;

    public FoodFacadeREST() {
        super(Food.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Food entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Food entity) {
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
    public Food find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByFoodId/{foodId}")
    @Produces({"application/json"})
    public List<Food> findByFoodId(@PathParam("foodId")int foodId){
        Query query = em.createNamedQuery("Food.findByFoodId");
        query.setParameter("foodId",foodId);
        return query.getResultList();
    }  
    
    @GET
    @Path("findByFoodName/{foodName}")
    @Produces({"application/json"})
    public List<Food> findByFoodName(@PathParam("foodName") String foodName){
        Query query = em.createNamedQuery("Food.findByFoodName");
        query.setParameter("foodName",foodName);
        return query.getResultList();
    }
  
    @GET
    @Path("findByFoodAmount/{foodAmount}")
    @Produces({"application/json"})
    public List<Food> findByFoodAmount(@PathParam("foodAmount")double foodAmount){
        Query query = em.createNamedQuery("Food.findByFoodAmount");
        query.setParameter("foodAmount",foodAmount);
        return query.getResultList();
    }
   
    @GET
    @Path("findByFoodquantityUnit/{foodquantityUnit}")
    @Produces({"application/json"})
    public List<Food> findByFoodquantityUnit(@PathParam("foodquantityUnit")String  foodquantityUint){
        Query query = em.createNamedQuery("Food.findByFoodquantityUnit");
        query.setParameter("foodquantityUnit",foodquantityUint);
        return query.getResultList();
    }
//    
//    @GET
//    @Path("findByFoodCategory/{foodFat}")
//    @Produces({"application/json"})
//    public List<Food> findByFoodCategory(@PathParam("foodFat")String categoryStr){
//        TypedQuery<Food> query = em.createQuery("SELECT f FROM Food f WHERE f.foodFat = :foodFat",Food.class);
//        int category = Integer.parseInt(categoryStr);
//        query.setParameter("foodFat",category);
//        return query.getResultList();
//    }
//    
     @GET
    @Path("findByFoodCategory/{foodFat}")
    @Produces({"application/json"})
    public List<Food> findByFoodCategory(@PathParam("foodFat")Integer  foodFat){
        Query query = em.createNamedQuery("Food.findByFoodFat");
        query.setParameter("foodFat",foodFat);
        return query.getResultList();
    }
    
    @GET
    @Path("findByFoodCarlorie/{foodCarlorie}")
    @Produces({"application/json"})
    public List<Food> findByFoodCalorie(@PathParam("foodCarlorie")Integer foodCarlorie){
        Query query = em.createNamedQuery("Food.findByFoodCalorie");
        query.setParameter("foodCalorie",foodCarlorie);
        return query.getResultList();
    }  
     
               
}
