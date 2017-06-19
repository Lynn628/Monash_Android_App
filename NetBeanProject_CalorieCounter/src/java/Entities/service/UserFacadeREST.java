/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.service;

import Entities.User;
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
@Path("entities.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "CalorieCounterPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, User entity) {
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
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByUserName/{userName}")
    @Produces({"application/json"})
    public List<User> findByUserName(@PathParam("userName")String userName){
        Query query = em.createNamedQuery("User.findByUserName");
        query.setParameter("userName",userName);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserId/{userId}")
    @Produces({"application/json"})
    public List<User> findByUserId(@PathParam("userId")Integer userId){
        Query query = em.createNamedQuery("User.findByUserId");
        query.setParameter("userId",userId);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserAge/{userAge}")
    @Produces({"application/json"})
    public List<User> findByUSerAge(@PathParam("userAge")Integer userAge){
        Query query = em.createNamedQuery("User.findByUserAge");
        query.setParameter("userAge",userAge);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserHeight/{userHeight}")
    @Produces({"application/json"})
    public List<User> findByUserHeight(@PathParam("userHeight")Integer userHeight){
        Query query = em.createNamedQuery("User.findByUserHeight");
        query.setParameter("userHeight",userHeight);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserWeight/{userWeight}")
    @Produces({"application/json"})
    public List<User> findByFoodCalorie(@PathParam("userWeight")Integer userWeight){
        Query query = em.createNamedQuery("User.findByUserWeight");
        query.setParameter("userWeight",userWeight);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserGender/{userGender}")
    @Produces({"application/json"})
    public List<User> findByUserGender(@PathParam("userGender")String userGender){
        Query query = em.createNamedQuery("User.findByUserGender");
        query.setParameter("userGender",userGender);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserlevelOfActivity/{userlevelOfActivity}")
    @Produces({"application/json"})
    public List<User> findByAUserlevelOfActivity(@PathParam("userlevelOfActivity")Integer userlevelOfActivity){
        Query query = em.createNamedQuery("User.findByUserlevelOfActivity");
        query.setParameter("userlevelOfActivity",userlevelOfActivity);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserstepPerMiles/{userstepPerMiles}")
    @Produces({"application/json"})
    public List<User> findByUserstepPerMiles(@PathParam("userstepPerMiles")Integer userstepPerMiles){
        Query query = em.createNamedQuery("User.findByUserstepPerMiles");
        query.setParameter("userstepPerMiles", userstepPerMiles);
        return query.getResultList();
    }
 
    @GET
    @Path("findByUserNameAndGender/{userName}/{userGender}")
    @Produces({"application/json"})
    public List<User> findByUserNameAndGender(@PathParam("userName")String userName,
            @PathParam("userGender")String userGender ){
            TypedQuery<User> query = 
                    em.createQuery("SELECT u FROM User u WHERE u.userName =:userName "
                            + "AND u.userGender = :userGender",User.class);
            query.setParameter("userName",userName);
            query.setParameter("userGender",userGender);
            return query.getResultList();
        }
    
    @GET
    @Path("login/{userName}")
    @Produces({"application/json"})
    public String login(@PathParam("userName") String userName) {
        TypedQuery<User> query = null;
        String result;
        query = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = '" + userName + "'",User.class);
        List<User> resultList = query.getResultList();
        if(resultList.size() == 0){
            result = "NOT FOUND";
            
        }  else{
            result = "OK";
        }
        return result;
    }
        
//        query = em.createQuery(
//                "SELECT u FROM User u WHERE u.userName = '" + userName + "' and u.password = '" + password + "'",User.class);
//        resultList = query.getResultList();
//        if(resultList.size() == 0){
//            result +=  "2}";
//        }else{
//            result +=  "1}";
//        }
//        return result;
//    }
    
     public List<User> queryByUserName(String userName){
        Query query
                    = em.createNamedQuery("User.findByUserName");
            query.setParameter("userName", userName);
        return query.getResultList();
    }
    
    @GET
    @Path("register/{userName}/{age}/{gender}/{height}/{weight}/{level}/{stepsPerMiles}")
    @Produces({"application/json"})
    public String register(@PathParam("userName") String userName, @PathParam("age") String age,
            @PathParam("gender") String gender, @PathParam("height") String height,
            @PathParam("weight") String weight,@PathParam("level") String level,@PathParam("stepsPerMiles")String steps) {
        List<User> users = queryByUserName(userName);
        String result = null;
        int idNum = 0;
        idNum += findAll().size();
        User user = new User();
        //String uuid = UUIDUtils.createUUID();
        user.setUserId(idNum);
        user.setUserName(userName);
        user.setUserAge(Integer.parseInt(age));
        user.setUserHeight(Integer.parseInt(height));
        user.setUserWeight(Float.parseFloat(weight));
        user.setUserlevelOfActivity(Integer.parseInt(level));
        user.setUserGender(gender);
        user.setUserstepPerMiles(Integer.parseInt(steps));
        
        em.persist(user); 
        //List<User> users = findAll();
       // result += "1}";
        result = "OK";
        return result;
    }

}

  
