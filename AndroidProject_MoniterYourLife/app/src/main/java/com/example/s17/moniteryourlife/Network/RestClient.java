package com.example.s17.moniteryourlife.Network;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import com.example.s17.moniteryourlife.Config;
import com.example.s17.moniteryourlife.FragementUtil.Report;
import com.example.s17.moniteryourlife.SQLite.USER;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by s17 on 4/20/2016.
 */
public class RestClient {
    private static final String BASE_URI = "http://172.16.120.60:8080/CalorieCounter/webresources/";
    public static String findUserByName(String userName){
        USER user = null;
       //path of the method
        final String methodPath = "entities.user/login/"+ userName;
//initialize
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
            // Making HTTP request
        try {
            String tmp = BASE_URI+methodPath;
            System.out.println(tmp);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add HTTP headers to set your respond type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the inputsteream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
//return null if an exception happened
        return textResult;
    }

    //POST method
   public static String insertUser(JSONObject userJson ){
        final String methodPath = "entities.user";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        // Making HTTP request
       try {
           url = new URL(BASE_URI + methodPath);
        //open the connection
           conn = (HttpURLConnection) url.openConnection();
        //set the timeout
           conn.setReadTimeout(10000);
           conn.setConnectTimeout(15000);
        //set the connection method to POST
           conn.setRequestMethod("POST");
        //set the output to true
           conn.setDoOutput(true);
        //set length of the data you are sending
         // * conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
        //add HTTP headers to set your respond type to json
           conn.setRequestProperty("Content-Type", "application/json");
        //send the POST out
           PrintWriter out = new PrintWriter(conn.getOutputStream());
           String userStr = userJson.toString();
           out.print(userStr);
           out.close();
        //Read the response
           Scanner inStream = new Scanner(conn.getInputStream());
        //read the input steream and store it as string
           while (inStream.hasNextLine()) {
               textResult += inStream.nextLine();
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           conn.disconnect();
       }
       return textResult;
     }

    //Update calorie goal
    public static String updateCalorieGoal(String userName, String dietDate, String calorieGoal){
        String methodPath = "entities.report/updateCalorieGoal/"+userName+"/"+dietDate+"/"+calorieGoal;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        return textResult;
    }

    //Get user calorie goal
    public static String getCalorieGoal(String userName, String dietDate){
        String methodPath = "entities.report/getCalorieGoal/"+userName+"/"+dietDate;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            System.out.println(">>>>>>>>>>>>>>>>>>>>Client1" + url);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            System.out.println(">>>>>>>>>>>>>>>>>>>>Client1");
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        return textResult;
    }

    //Put user daily totoal steps to server
    public static String daliySteps(String userName, String dietDate, String steps){
        String methodPath = "entities.report/updateTotalSteps/"+userName+"/"+dietDate+"/"+steps;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        return textResult;
    }

    //
    //Create user's new report entry in server database
    public static String addUserToReport(String userName, String dietDate){
        String methodPath = "entities.report/createUserReport/"+userName+"/"+dietDate;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            System.out.println(">>>>>>>>>>>>>>"+url);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>textResult"+textResult);
        return textResult;
    }


    //Get user's report
    public static String getReport(String userName, String date){
        String methodPath = "entities.report/getReport/"+userName+"/"+date;
       // String methodPath = "entities.report/getReport/"+userName+"/2016-03-01";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            System.out.println(">>>>>>>>>>>>>>"+url);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>textResult"+textResult);
        return textResult;
    }


    //Used to find foods in a specific category
    public static String findFoodByCategory(Integer category){
        String categoryStr = category.toString();
        String methodPath = "entities.food/findByFoodCategory/"+categoryStr;
        // String methodPath = "entities.report/getReport/"+userName+"/2016-03-01";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            System.out.println(">>>>>>>>>>>>>>"+url);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>textResult"+textResult);
        return textResult;
    }


    //Add food to diet table in server side
    public static String addFoodToDiet(String userName, String dietDate, String foodName, String foodAmount){
        String methodPath = "entities.diet/addFoodToDiet/"+userName+"/"+dietDate+"/"+foodName+"/"+ foodAmount;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            System.out.println(">>>>>>>>>>>>>>"+url);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>textResult"+textResult);
        return textResult;
      }



    //Request for food calorie detail in National nurient database
    public static String requsetNurientDetial(String foodIdStr){
       // String BASE = "http://api.nal.usda.gov/ndb/reports/?ndbno=01009&type=b&format=json&api_key=cNupiw9hXjX3aHdvvGHAQDWh43FW6fB0Co7cMbzZ";
        HashMap<String,String> foodIdMatch = new HashMap<>();
        String BASE = "http://api.nal.usda.gov/ndb/reports/?ndbno="+foodIdStr+
                "&type=f&format=json&api_key=cNupiw9hXjX3aHdvvGHAQDWh43FW6fB0Co7cMbzZ";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
           // url = new URL(BASE + methodPath);
            url = new URL(BASE);
            System.out.println(">>>>>>>>>>>>>>"+url);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>textResult"+textResult);
        return textResult;
    }

    public static String queryUserReportDuringPeriod(String userName, String beginDate, String endDate){
        String methodPath = "entities.report/queryReportDuringPeriod/"+userName+"/"+beginDate+"/"+endDate;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            System.out.println(">>>>>>>>>>>>>>"+url);
            //Open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout to true
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //Set the connection method
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //Read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>textResult"+textResult);
        return textResult;
    }

   }



