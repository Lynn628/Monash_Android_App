/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.service;

import Entities.Diet;
import Entities.Report;
import Entities.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
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
@Path("entities.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "CalorieCounterPU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
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
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
   @Path("findByReportId/{reportId}")
   @Produces("application/json")
   public List<Report> findByReportId(@PathParam("reportId")Integer reportId){
       Query query = em.createNamedQuery("Report.findByReportId");
       query.setParameter("reportId", reportId);
       return query.getResultList();
   }
   
   @GET
   @Path("findByReportcarlorieConsumed/{reportcarlorieConsumed}")
   @Produces("application/json")
   public List<Report> findByReportcarlorieConsumed(@PathParam("reportcarlorieConsumed")Integer reportcarlorieConsumed){
       Query query = em.createNamedQuery("Report.findByReportcarlorieConsumed");
       query.setParameter("reportcarlorieConsumed", reportcarlorieConsumed);
       return query.getResultList();
   }
   
   @GET
   @Path("findByReportcarlorieBurned/{reportcarlorieBurned}")
   @Produces("application/json")
   public List<Report> findByReportcarlorieBurned(@PathParam("reportcarlorieBurned")Integer reportcarlorieBured){
       Query query = em.createNamedQuery("Report.findByReportcarlorieBurned");
       query.setParameter("reportcarlorieBurned", reportcarlorieBured);
       return query.getResultList();
   }
   
   @GET
   @Path("findByReporttotalSteps/{reporttotalSteps}")
   @Produces("application/json")
   public List<Report> findByReporttotalSteps(@PathParam("reporttotalSteps")Integer reporttotalSteps){
       Query query = em.createNamedQuery("Report.findByReporttotalSteps");
       query.setParameter("reporttotalSteps", reporttotalSteps);
       return query.getResultList();
   }
     
   @GET
   @Path("findByReportGoal/{reportGoal}")
   @Produces("application/json")
   public List<Report> findByReportGoal(@PathParam("reportGoal")Integer reportGoal){
       Query query = em.createNamedQuery("Report.findByReportGoal");
       query.setParameter("reportGoal",reportGoal);
       return query.getResultList();
   }

   @GET
   @Path("findByReportRemaining/{reportRemaining}")
   @Produces("application/json")
   public List<Report> findByReportRemaining(@PathParam("reportRemaining")Integer reportRemaining){
       Query query = em.createNamedQuery("Report.findByReportRemaining");
       query.setParameter("reportRemaining", reportRemaining);
       return query.getResultList();
   }
   
   @GET
   @Path("findByUserId/{userId}")
   @Produces("application/json")
   public List<Report> finByUserId(@PathParam("userId")Integer userId){
       Query query = em.createNamedQuery("Report.finByUserId");
       query.setParameter("userId", userId);
       return query.getResultList();
   }
   
   @GET
   @Path("findByDietDate/{dietDate}")
   @Produces("application/json")
   public List<Report> findByDieDate(@PathParam("dietDate")String dietDate) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date javaDate = dateFormat.parse(dietDate); 
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
        Query query = em.createNamedQuery("Diet.findByDietDate");
        query.setParameter("dietDate",sqlDate);
       return query.getResultList();
   }
   
    
    @GET
    @Path("findByUserIdAndDietDate/{userId}/{dietDate}")
    @Produces({"application/json"})
    public Report findByUserIdAndDietDate(@PathParam("userId")Integer userId,
            @PathParam("dietDate")String dietDate ){
           java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
            TypedQuery<Report> query = 
                    em.createQuery("SELECT r FROM Report r WHERE r.dietDate = :dietDate "
                                    + "AND r.userId.userId = :userId",Report.class);
            query.setParameter("userId",userId);
            query.setParameter("dietDate",sqlDate);
            return query.getSingleResult();
        }
    
   
    @GET
    @Path("fingByUserNameAndGoal/{userName}/{reportGoal}")
    @Produces({"application/json"})
    public List<Report> findByUserNameAndGoal(@PathParam("userName")String userName, 
            @PathParam("reportGoal")Integer reportGoal){
        Query query = em.createNamedQuery("Report.findByUserNameAndGoal");
        query.setParameter("userName", userName);
        query.setParameter("reportGoal", reportGoal);
        return query.getResultList();
    }
     
    @GET
    @Path("calcBMR/{userId}")
    @Produces({"application/json"})
    public String calcBMR(@PathParam("userId")Integer userId){
        Query query = em.createNamedQuery("User.findByUserId");
        query.setParameter("userId",userId);
        List<User> userList = new LinkedList<User> (query.getResultList());
        double bmr = 0;
        for(int i = 0; i < userList.size(); i++){
            User thisUser = userList.get(i);
            if(thisUser.getUserGender().equals("M") ){
            bmr = (13.75 * thisUser.getUserWeight()) + (5 * thisUser.getUserHeight())    
                    - ( 6.76 * thisUser.getUserAge()) + 66 ;  
        }else if(thisUser.getUserGender().equals("F")){
            bmr = (9.56 * thisUser.getUserWeight()) + (1.85 * thisUser.getUserHeight())
                    - (4.68 * thisUser.getUserAge()) + 655 ;
            }  
        }
        String bmrStr = String.valueOf(bmr);
        return bmrStr;
     }
    
    @GET
    @Path("calcCalorieAtRest/{userId}")
    @Produces({"application/Json"})
    public String calcCalorieAtRest(@PathParam("userId")Integer userId){
        Query query = em.createNamedQuery("User.findByUserId");
        query.setParameter("userId",userId);
        List<User> userList = new LinkedList<User> (query.getResultList()); 
        double  calorieAtRest = 0;
          for(int i = 0; i < userList.size(); i++){
            User thisUser = userList.get(i);
            Double userBMR = Double.parseDouble(calcBMR(userId));
        switch(thisUser.getUserlevelOfActivity().intValue()){
            case 1:{
                calorieAtRest = userBMR * 1.2;
                break;
            }
            case 2:{
                calorieAtRest = userBMR * 1.375;
                break;
            }
            case 3:{
                calorieAtRest = userBMR * 1.55;
                break;
            }
            case 4:{
                calorieAtRest = userBMR * 1.725;
                break;
            }
            case 5:{
                calorieAtRest = userBMR * 1.9;
                break;
            }
            default:
                System.out.println("no such level of activity");
                break;
        }    
    }
        String calorieAtRestStr = String.valueOf(calorieAtRest) ;
        return calorieAtRestStr ;
    }
    
   @GET
   @Path("calcDailyCaloriedConsumed/{userId}/{dietDate}")
   @Produces({"application/json"})
    public String calcDailyCalorieConsumed(@PathParam("userId")Integer userId,
           @PathParam("dietDate")String dietDate ){
          java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
            TypedQuery<Diet> query = 
                    em.createQuery("SELECT d FROM Diet d WHERE d.dietDate = :dietDate "
                           + "AND d.userId.userId = :userId",Diet.class);
           query.setParameter("userId",userId);
           query.setParameter("dietDate",sqlDate);
           List<Diet> userDiet = new LinkedList<Diet> (query.getResultList());
            double dailyCalorieConsume = 0;
            
            for(int i = 0; i < userDiet.size(); i++){
               int foodCarlorie = userDiet.get(i).getFoodName().getFoodCalorie();
               double foodUnit = userDiet.get(i).getFoodName().getFoodAmount();
               dailyCalorieConsume  += (userDiet.get(i).getFoodAmount() / foodUnit) * foodCarlorie;
          }    
           // Insert  back the consume result to the report table according to the userId and dietDate
              findByUserIdAndDietDate(userId, dietDate).setReportcarlorieConsumed(dailyCalorieConsume);
              String dailyCalorieConsumeStr =String.valueOf(dailyCalorieConsume);
              return dailyCalorieConsumeStr;
       }  
    
  
    public String calcCaloriePerSteps(Integer userId , String dietDate, Integer totalSteps  ){
          TypedQuery<User> query1 = em.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class);
          query1.setParameter("userId",userId);
          Float userWeightKg = query1.getSingleResult().getUserWeight();
          Double userWeightBl = userWeightKg * 2.2046;
          Double caloriePerMile = userWeightBl * 0.49;
          Integer stepsPerMile = query1.getSingleResult().getUserstepPerMiles(); 
         // Integer userTotalSteps = findByUserIdAndDietDate(userId, dietDate).getReporttotalSteps();        
          Integer userTotalSteps = totalSteps;
          Double totalCalorie = caloriePerMile * userTotalSteps / stepsPerMile;
          String totalCalorieStr = String.valueOf(totalCalorie);
          return totalCalorieStr;     
    }
    
 
    public String calcDailyCalorieBurned(Integer userId , String dietDate, Integer totalSteps ){
        java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
        Double userBurned = Double.parseDouble(calcCalorieAtRest(userId))
                           + Double.parseDouble(calcCaloriePerSteps(userId ,dietDate, totalSteps ));
        String userBurnedStr = String.valueOf(userBurned);
        findByUserIdAndDietDate(userId, dietDate).setReportcarlorieBurned(userBurned);
        return userBurnedStr;
    }
     
    @GET
    @Path("calcCalorieConsumAndBurned/{userId}/{dietDate}")
    @Produces({MediaType.TEXT_PLAIN})
    public String calcCalorieConsumAndBurned(@PathParam("userId")Integer userId ,
           @PathParam("dietDate")String dietDate ){
         Double userConsumed = Double.parseDouble(calcDailyCalorieConsumed(userId, dietDate));
           TypedQuery<Report> query = 
                    em.createQuery("SELECT r FROM Report r WHERE r.dietDate = :dietDate "
                                    + "AND r.userId = :userId",Report.class);
           query.setParameter("userId", userId);
           query.setParameter("dietDate",dietDate);
           Integer totalSteps = query.getSingleResult().getReporttotalSteps();
         Double userBurned  = Double.parseDouble(calcDailyCalorieBurned(userId, dietDate, totalSteps));
         String userConsumedStr = String.valueOf(userConsumed);
         String userBurnedStr = String.valueOf(userBurned);
         return "Daily calorie consumed : " +userConsumedStr + " , " +" Daily calorie burned: " +userBurnedStr;
    }
    
   
    @GET  
    @Path("calcCalorieDuringPeriod/{userId}/{dietBegin}/{dietEnd}")
    @Produces("application/json")
    public String calcCalorieDuringPeriod(@PathParam("userId") Integer userId,@PathParam("dietBegin") String dietBegin,
            @PathParam("dietEnd") String dietEnd) {
        java.sql.Date sqlBegin = java.sql.Date.valueOf(dietBegin);
        java.sql.Date sqlEnd = java.sql.Date.valueOf(dietEnd);
        TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r WHERE r.userId.userId = :userId "
                + "AND  r.dietDate BETWEEN :dietBegin AND :dietEnd", Report.class);
        query.setParameter("dietBegin", sqlBegin);
        query.setParameter("dietEnd", sqlEnd);
        query.setParameter("userId", userId);
        List<Report> userReportsList = query.getResultList();
        double totalConsumed = 0;
        double totalBurned = 0; 
        for(int i =0; i < userReportsList.size(); i++){
            totalConsumed += userReportsList.get(i).getReportcarlorieConsumed();
            totalBurned += userReportsList.get(i).getReportcarlorieBurned();
       }
      String totalConsumedStr = String.valueOf(totalConsumed);
      String totalBurnedStr = String.valueOf(totalBurned);
       return "Total calorie consumed : " +totalConsumedStr + " , " +" total calorie burned: "+totalBurnedStr;
    }
    
    @GET
    @Path("updateCalorieGoal/{userName}/{dietDate}/{calorieGoal}")
    //@Produces("application/json")
    @Produces({MediaType.APPLICATION_JSON})
    public String updateCarlorieGoal(@PathParam("userName")String userName ,
         @PathParam("dietDate")String dietDate, @PathParam("calorieGoal")String calorieGoal ){
           java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
            TypedQuery<Report> query = 
                    em.createQuery("SELECT r FROM Report r WHERE r.dietDate = :dietDate "
                                    + "AND r.userId.userName = :userName",Report.class);
            query.setParameter("dietDate", sqlDate);
            query.setParameter("userName",userName);
            Double goal = Double.parseDouble(calorieGoal);
            query.getSingleResult().setReportGoal(goal);
            return "OK";
    }
   
    /**
     * Method to update user's total steps on a specific date 
     * @param userName
     * @param dietDate
     * @param totalSteps
     * @return 
     */
    @GET
    @Path("updateTotalSteps/{userName}/{dietDate}/{totalSteps}")
    @Produces("application/json")
    public String updateTotalSteps(@PathParam("userName")String userName ,
         @PathParam("dietDate")String dietDate, @PathParam("totalSteps")String totalSteps ){
           java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
            TypedQuery<Report> query = 
                    em.createQuery("SELECT r FROM Report r WHERE r.dietDate = :dietDate "
                                    + "AND r.userId.userName = :userName",Report.class);
            query.setParameter("dietDate", sqlDate);
            query.setParameter("userName",userName);
            Integer steps = Integer.parseInt(totalSteps);
            User userId = query.getSingleResult().getUserId();
            query.getSingleResult().setReporttotalSteps(steps);
            //Calculate the total calorie burned once the total steps is accessible
           String calorieBurnedStr = calcDailyCalorieBurned(userId.getUserId(), dietDate, steps);
           Double calorieBurned = Double.parseDouble(calorieBurnedStr);
           query.getSingleResult().setReportcarlorieBurned(calorieBurned);
           return "OK";
    }      
    
    /**
     * Method to get user's calorie goal on a specific date.
     * @param userName
     * @param dietDate
     * @param totalSteps
     * @return 
     */
    @GET
    @Path("getCalorieGoal/{userName}/{dietDate}")
    @Produces("application/json")
    public String getCalorieGoal(@PathParam("userName")String userName ,@PathParam("dietDate")String dietDate ){
           java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
            TypedQuery<Report> query = 
                    em.createQuery("SELECT r FROM Report r WHERE r.dietDate = :dietDate "
                                    + "AND r.userId.userName = :userName",Report.class);
            query.setParameter("dietDate", sqlDate);
            query.setParameter("userName",userName);
//            Double goal = query.getSingleResult().getReportGoal();
            if(query.getResultList().size()==0){
                 TypedQuery<Report> query2 = 
                    em.createQuery("SELECT r FROM Report r WHERE r.userId.userName = :userName",Report.class);
                query2.setParameter("userName",userName);
                User userId = query2.getResultList().get(0).getUserId();
                Report report = new Report();
                report.setUserId(userId);
                report.setDietDate(sqlDate);
                super.create(report);
                return "Not Found";
            }else{
                Double goal = query.getSingleResult().getReportGoal();
                String goalStr = goal.toString();
                return goalStr;
            }
    } 
    
    @GET
    @Path("createUserReport/{userName}/{dietDate}")
    @Produces("application/json")
    public String createUserReport(@PathParam("userName")String userName ,@PathParam("dietDate")String dietDate ){
           java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
            TypedQuery<Report> query = 
                    em.createQuery("SELECT r FROM Report r WHERE r.dietDate = :dietDate "
                                    + "AND r.userId.userName = :userName",Report.class);
            query.setParameter("dietDate", sqlDate);
            query.setParameter("userName",userName);
//            Double goal = query.getSingleResult().getReportGoal();
            if(query.getResultList().size()==0){
                 TypedQuery<User> query2 = 
                    em.createQuery("SELECT u FROM User u WHERE u.userName = :userName",User.class);
                query2.setParameter("userName",userName);
                Report report = new Report();
                report.setUserId(query2.getResultList().get(0));
                report.setDietDate(sqlDate);
                super.create(report);
            }
                return "Success";
    } 
    
    /**
     * find user's report according to username and date
     * @param userId
     * @param dietDate
     * @return 
     */
    @GET
    @Path("getReport/{userName}/{dietDate}")
    @Produces({"application/json"})
    public Report getReport(@PathParam("userName")String userName,
            @PathParam("dietDate")String dietDate ){
             TypedQuery<User> query1 = 
                     em.createQuery("SELECT u FROM User u WHERE u.userName = :userName",User.class);
             query1.setParameter("userName",userName);
             Integer userId = query1.getResultList().get(0).getUserId();
             //Calcultae user daily calorie consume
             String dailyConsumeStr = calcDailyCalorieConsumed(userId,dietDate);
             Double dailyConsume = Double.parseDouble(dailyConsumeStr);
             java.sql.Date sqlDate = java.sql.Date.valueOf(dietDate);
             TypedQuery<Report> query2 = 
                    em.createQuery("SELECT r FROM Report r WHERE r.dietDate = :dietDate "
                                    + "AND r.userId.userName = :userName",Report.class);
            query2.setParameter("userName",userName);
            query2.setParameter("dietDate",sqlDate);
            if((query2.getSingleResult().getReportGoal() != null)&&
                    (query2.getSingleResult().getReportcarlorieBurned() != null)){
                 double remain = query2.getResultList().get(0).getReportGoal()-
                   query2.getResultList().get(0).getReportcarlorieBurned()+
                   dailyConsume;
                  query2.getSingleResult().setReportRemaining(remain);
            }else{
                 query2.getSingleResult().setReportRemaining(0.0);
            }
            query2.getSingleResult().setReportcarlorieConsumed(dailyConsume);
           return query2.getSingleResult();
    
        }
    
    @GET  
    @Path("queryReportDuringPeriod/{userName}/{dietBegin}/{dietEnd}")
    @Produces("application/json")
    public List<Report> queryReportDuringPeriod(@PathParam("userName") String userName,@PathParam("dietBegin") String dietBegin,
            @PathParam("dietEnd") String dietEnd) {
        java.sql.Date sqlBegin = java.sql.Date.valueOf(dietBegin);
        java.sql.Date sqlEnd = java.sql.Date.valueOf(dietEnd);
        TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r WHERE r.userId.userName = :userName "
                + "AND  r.dietDate BETWEEN :dietBegin AND :dietEnd", Report.class);
        query.setParameter("dietBegin", sqlBegin);
        query.setParameter("dietEnd", sqlEnd);
        query.setParameter("userName", userName);
        List<Report> userReportsList = query.getResultList();
      return query.getResultList();
       
    }
}
