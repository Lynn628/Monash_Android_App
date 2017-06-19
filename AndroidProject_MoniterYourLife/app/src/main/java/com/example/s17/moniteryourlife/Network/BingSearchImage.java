package com.example.s17.moniteryourlife.Network;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by s17 on 4/27/2016.
 */
public class BingSearchImage {
    public static String  bingSearchImage(String foodName){
        // String searchText = "bread";
        String searchText = foodName;
        HttpURLConnection conn = null;
        StringBuilder sb = new StringBuilder();
        searchText = searchText.replaceAll(" ", "%20");
        //        String customerId = "0d41385d-6eb6-4857-9cd0-3c29c1a7ec52";
        String accountKey = "Yu3hNyIII6ULMFyNcRwT8hXCyQ0hh/dygxo4+G6JgU0";

        byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
        String accountKeyEnc = new String(accountKeyBytes);
        URL url;
        try {
//            &Sources=%27image%27
            String path = "https://api.datamarket.azure.com/Bing/Search/Image?Query=%27" + searchText + "%27&$top=10&$format=JSON&Market=%27en-us%27";
            System.out.println("path:" + path);
            url = new URL(path);
            // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
//            conn.setRequestProperty("Accept", "application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
            //
            String output;
            System.out.println("Output from Server .... \n");
            char[] buffer = new char[4096];
            while ((output = reader.readLine()) != null) {
                sb.append(output);
            }

            // conn.disconnect();
            System.out.println(sb.toString());
/*
           int find = sb.indexOf("<d:Description");
            int total = find + 1000;
            System.out.println("Find index: " + find);
            System.out.println("Total index: " + total);
            sb.getChars(find + 35, total, buffer, 0);
            String str = new String(buffer);

            int find2 = str.indexOf("</d:Description>");
            int total2 = find2 + 400;
            System.out.println("Find index: " + find);
            System.out.println("Total index: " + total);
            char[] buffer2 = new char[1024];
            str.getChars(0, find2, buffer2, 0);
            String str2 = new String(buffer2);
            str2 = Jsoup.parse(str2).text();
            System.out.println(str2);*/
//    } catch (MalformedURLException e1) {
//        // TODO Auto-generated catch block
//        e1.printStackTrace();
//    } catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        return sb.toString() ;
    }
}

